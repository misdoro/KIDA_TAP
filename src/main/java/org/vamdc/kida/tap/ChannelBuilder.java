package org.vamdc.kida.tap;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.kida.dao.*;
import org.vamdc.kida.xsams.BiblioSource;
import org.vamdc.kida.xsams.KidaAtom;
import org.vamdc.kida.xsams.KidaFunction;
import org.vamdc.kida.xsams.KidaMolecule;
import org.vamdc.kida.xsams.KidaParticle;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.query.QueryMapper;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;
import org.vamdc.xsams.common.ArgumentType;
import org.vamdc.xsams.common.DataSetType;
import org.vamdc.xsams.common.DataSetsType;
import org.vamdc.xsams.common.FitDataType;
import org.vamdc.xsams.common.FitParametersType;
import org.vamdc.xsams.common.NamedDataType;
import org.vamdc.xsams.common.ValueType;
import org.vamdc.xsams.schema.FunctionType;
import org.vamdc.xsams.functions.Functions;
import org.vamdc.xsams.process.collisions.CollisionalProcessClassType;
import org.vamdc.xsams.process.collisions.CollisionalTransitionType;
import org.vamdc.xsams.schema.DataDescriptionType;
import org.vamdc.xsams.sources.SourceType;
import org.vamdc.xsams.util.IDs;

public class ChannelBuilder {

	private static void writeReactantProduct(Collection<ChannelHasSpecie> chsc,
			Vector<Integer> tabSpeciesId,
			CollisionalTransitionType mycollision, RequestInterface request) {
		for (ChannelHasSpecie chs : chsc) {

			// check if species have not been added
			if (!tabSpeciesId.contains(new Integer(chs.getSpecie().getId()))) {
				Specie forgotSpecies = chs.getSpecie();
				if (forgotSpecies.isASpecialSpecies()) {
					request.getXsamsManager().addElement(new KidaParticle(forgotSpecies));
				} else if (forgotSpecies.isAnAtom()) {
					request.getXsamsManager().addElement(new KidaAtom(forgotSpecies));
				} else {
					request.getXsamsManager().addElement(new KidaMolecule(forgotSpecies));
				}
				tabSpeciesId.addElement(new Integer(forgotSpecies.getId()));
			}

			if (chs.getType().equals(Channel.REACTANT)) {
				int count = chs.getOccurrence();
				for (int i=0;i<count;i++){
					mycollision
					.getReactants()
					.add(request.getXsamsManager().getSpeciesRef(
							IDs.getSpecieID(chs.getSpecie().getId())));
				}

			} else if (chs.getType().equals(Channel.PRODUCT)) {
				int count = chs.getOccurrence();
				for (int i=0;i<count;i++){

					mycollision
					.getProducts()
					.add(request.getXsamsManager().getSpeciesRef(
							IDs.getSpecieID(chs.getSpecie().getId())));
				}

			}

		}

	}

	public static SelectQuery getCayenneQuery(Query query) {
		Expression channelExp = null;
		Expression prefExp = null;
		// Loop over all defined prefixes
		Vector<String> aliases = new Vector<String>();
		for (Prefix pref : query.getPrefixes()) {
			VSSPrefix prefix = pref.getPrefix();
			int index = pref.getIndex();

			// Add alias to vector
			String strPrefix = prefix.name() + index;
			aliases.add(strPrefix);

			if (prefix == VSSPrefix.REACTANT) {// Handle REACTANT
				Expression chsex = ExpressionFactory.matchExp(strPrefix
						+ ".type", Channel.REACTANT);
				prefExp = QueryMapper.mapTree(
						query.getPrefixedTree(prefix, index),
						Restrictables.getAliasedChannelMap(strPrefix));// Build
				// tree
				// using
				// aliases
				if (prefExp != null)
					prefExp = prefExp.andExp(chsex);

			} else if (prefix == VSSPrefix.PRODUCT) {// Handle PRODUCT
				Expression chsex = ExpressionFactory.matchExp(strPrefix
						+ ".type", Channel.PRODUCT);
				prefExp = QueryMapper.mapTree(
						query.getPrefixedTree(prefix, index),
						Restrictables.getAliasedChannelMap(strPrefix));// Build
				// tree
				// using
				// aliases
				if (prefExp != null)
					prefExp = prefExp.andExp(chsex);
			} else {
				prefExp = null;
			}
			if (channelExp == null) {// Channel exp is yet empty, just assign
				// prefExp to it.
				channelExp = prefExp;
				prefExp = null;
			} else if (prefExp != null) {
				channelExp = channelExp.andExp(prefExp);//
			}
		}

		// add all keywords that don't require or don't have a prefix.
		prefExp = QueryMapper.mapTree(query.getPrefixedTree(null, 0),
				Restrictables.getAliasedChannelMap("unprefixed"));
		aliases.add("unprefixed");

		if (channelExp == null) {// Channel exp is yet empty, just assign
			// prefExp to it.
			channelExp = prefExp;
			prefExp = null;
		} else if (prefExp != null) {
			channelExp = channelExp.andExp(prefExp);
		}

		System.out.println("Expression:" + channelExp);
		SelectQuery q = new SelectQuery(Channel.class, channelExp);

		if (aliases.size() > 0)
			q.aliasPathSplits("channelHasSpecies",
					aliases.toArray(new String[0]));

		return q;

	}

	public static void buildChannels(RequestInterface request,
			Vector<Integer> tabSpeciesId) {

		SelectQuery atquery = getCayenneQuery(request.getQuery());
		
		@SuppressWarnings("unchecked")
		List<Channel> atms = (List<Channel>) request.getCayenneContext()
				.performQuery(atquery);

		for (Channel chan : atms) {
			if (chan.getAddedStatus() == 0)
				continue;
			TypeChannel tc = chan.getTypeChannel();
			if (tc == null || tc.getAbbrev().equals("3-body"))
				continue;

			CollisionalTransitionType mycollision = new CollisionalTransitionType();

			mycollision.setId(IDs.getProcessID('C', chan.getId()));

			Collection<ChannelHasSpecie> chsc = chan.getChannelHasSpecies();
			writeReactantProduct(chsc, tabSpeciesId, mycollision, request);

			CollisionalProcessClassType process = new CollisionalProcessClassType();

			ToolsBuilder.writeIAEACodes(process, chan.getTypeChannel()
					.getId());
			ToolsBuilder.writeProcessCodes(process, chan.getTypeChannel()
					.getId());

			mycollision.setProcessClass(process);

			DataSetsType datasets = new DataSetsType();

			Collection<ChannelValue> tChannelValues = chan
					.getChannelValues();
			// for all channel values for this reaction
			writeChanneValues(tChannelValues, request,
					mycollision, chan, datasets);

			mycollision.setDataSets(datasets);

			request.getXsamsManager().addProcess(mycollision);
		}

	}

	private static void writeChanneValues(
			Collection<ChannelValue> tChannelValues, RequestInterface request,
			CollisionalTransitionType mycollision, Channel chan,
			DataSetsType datasets) {
		for (ChannelValue channelValue : tChannelValues) {

			// ignore some channels
			if (!checkChannelValueValid(channelValue))
				continue;

			DataSetType channelValueDataSet = new DataSetType();
			channelValueDataSet
			.setDataDescription(DataDescriptionType.RATE_COEFFICIENT);
			FitDataType values = new FitDataType();

			// Bibliography
			Biblio cvBiblio = channelValue.getBibliography();
			if (cvBiblio != null) {
				SourceType bibliography = new BiblioSource(cvBiblio);
				request.getXsamsManager().addSource(bibliography);
				mycollision.addSource(bibliography);
			}

			// channel value
			if (channelValue.getCreatedAt() != null) {
				FitDataType fitData = new FitDataType();
				GregorianCalendar gCalendar = new GregorianCalendar();
				gCalendar.setTime(channelValue.getCreatedAt());
				XMLGregorianCalendar xmlCalendar = null;
				try {
					xmlCalendar = DatatypeFactory.newInstance()
							.newXMLGregorianCalendar(gCalendar);
				} catch (DatatypeConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fitData.setProductionDate(xmlCalendar);
			}

			values.setFitParameters(writeFitParameters(channelValue, chan,request));
			channelValueDataSet.getFitDatas().add(values);
			datasets.getDataSets().add(channelValueDataSet);

		}

	}

	private static FitParametersType writeFitParameters(
			ChannelValue channelValue, Channel chan, RequestInterface request) {
		FitParametersType result = new FitParametersType();

		String funcId = IDs.getFunctionID(channelValue.getFormula().getId());
		FunctionType func = request.getXsamsManager().getFunction(funcId);
		if (func==null){
			func = new KidaFunction(channelValue.getFormula());
			request.getXsamsManager().addFunction(func);
		}
		result.setFunctionRef(func);

		ArgumentType temperature = buildTemperatureArgument(channelValue);

		result.getFitArguments().add(temperature);

		NamedDataType alpha = new NamedDataType();
		alpha.setValue(new ValueType(channelValue.getValue("alpha"), chan
				.getUnitAlpha()));
		alpha.setName("alpha");
		result.getFitParameters().add(alpha);
		NamedDataType beta = new NamedDataType();
		beta.setValue(new ValueType(channelValue.getValue("beta"), "undef"));
		beta.setName("beta");
		result.getFitParameters().add(beta);
		NamedDataType gamma = new NamedDataType();
		gamma.setValue(new ValueType(channelValue.getValue("gamma"), "keV"));
		gamma.setName("gamma");
		result.getFitParameters().add(gamma);

		return result;

	}

	private static ArgumentType buildTemperatureArgument(
			ChannelValue channelValue) {
		ArgumentType temperature = new ArgumentType();
		temperature.setUnits("K");
		temperature.setName("T");

		temperature.setLowerLimit(channelValue.getValidityRange().getTmin().doubleValue());
		temperature.setUpperLimit(channelValue.getValidityRange().getTmax().doubleValue());
		return temperature;
	}

	private static boolean checkChannelValueValid(ChannelValue channelValue) {
		if (channelValue.getIsTrash() == 1)
			return false;
		if (channelValue.getIsTemp() == 1)
			return false;
		if (channelValue.getStatus() == 0)
			return false;

		return true;
	}

}
