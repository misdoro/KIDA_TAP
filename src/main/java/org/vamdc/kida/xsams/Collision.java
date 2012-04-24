package org.vamdc.kida.xsams;

import java.util.ArrayList;
import java.util.Collection;

import org.vamdc.kida.dao.Channel;
import org.vamdc.kida.dao.ChannelHasSpecie;
import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.kida.tap.SpeciesBuilder;
import org.vamdc.kida.tap.ToolsBuilder;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.DataSetType;
import org.vamdc.xsams.common.DataSetsType;
import org.vamdc.xsams.process.collisions.CollisionalProcessClassType;
import org.vamdc.xsams.process.collisions.CollisionalTransitionType;
import org.vamdc.xsams.util.IDs;

public class Collision extends CollisionalTransitionType{

	public Collision(Channel channel, XSAMSManager document){
		this.setId(IDs.getProcessID('C', channel.getId()));

		writeReactantProduct(channel.getChannelHasSpecies(), document);

		CollisionalProcessClassType process = new CollisionalProcessClassType();
		this.setProcessClass(process);
		ToolsBuilder.writeIAEACodes(process, channel.getTypeChannel().getId());
		ToolsBuilder.writeProcessCodes(process, channel.getTypeChannel().getId());

		DataSetsType datasets = new DataSetsType();
		this.setDataSets(datasets);
		datasets.getDataSets().addAll(getDataSets(channel,document));

	}

	private static Collection<DataSetType> getDataSets(
			Channel channel, XSAMSManager document) {
		Collection<DataSetType> result = new ArrayList<DataSetType>();

		for (ChannelValue channelValue : channel.getChannelValues()) {
			// ignore some channels
			if (!checkChannelValueValid(channelValue))
				continue;

			result.add(new FitDataSet(channelValue,document));
		}
		return result;
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

	private void writeReactantProduct(Collection<ChannelHasSpecie> chsc, XSAMSManager document) {
		for (ChannelHasSpecie chs : chsc) {

			// check if species have not been added
			String speciesID = IDs.getSpecieID(chs.getSpecieId());
			if (document.getElement(speciesID)==null){
				document.addElement(
						SpeciesBuilder.getKidaSpecies(chs.getSpecie()));
			}

			if (chs.getType().equals(Channel.REACTANT)) {
				int count = chs.getOccurrence();
				for (int i=0;i<count;i++){
					this.getReactants()
					.add(document.getSpeciesRef(speciesID));
				}

			} else if (chs.getType().equals(Channel.PRODUCT)) {
				int count = chs.getOccurrence();
				for (int i=0;i<count;i++){
					this.getProducts()
					.add(document.getSpeciesRef(speciesID));
				}

			}

		}

	}

}
