package org.vamdc.kida.xsams;

import java.util.Collection;

import org.vamdc.kida.constants.ProcessCodes;
import org.vamdc.kida.dao.Channel;
import org.vamdc.kida.dao.ChannelHasSpecie;
import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.kida.tap.SpeciesBuilder;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.DataSetsType;
import org.vamdc.xsams.process.collisions.CollisionalProcessClassType;
import org.vamdc.xsams.process.collisions.CollisionalTransitionType;
import org.vamdc.xsams.util.IDs;

public class Collision extends CollisionalTransitionType{

	public Collision(Channel channel, RequestInterface request){
		this.setId(IDs.getProcessID('C', channel.getId()));

		writeReactantProduct(channel.getChannelHasSpecies(), request);

		CollisionalProcessClassType process = new CollisionalProcessClassType();
		this.setProcessClass(process);
		ProcessCodes.writeIAEACodes(process, channel.getTypeChannel().getId());
		ProcessCodes.writeProcessCodes(process, channel.getTypeChannel().getId());

		this.setDataSets(getDataSets(channel.getChannelValues(),request.getXsamsManager()));

	}

	private DataSetsType getDataSets(
			Collection<ChannelValue> values, XSAMSManager document) {
		
		DataSetsType result = new DataSetsType();
		for (ChannelValue channelValue : values) {
			if (channelValue.isValid())
				result.getDataSets().add(new FitDataSet(channelValue,document));
		}
		return result;
		
	}

	private void writeReactantProduct(Collection<ChannelHasSpecie> chsc, RequestInterface request) {
		XSAMSManager document = request.getXsamsManager();
		for (ChannelHasSpecie chs : chsc) {

			// check if species have not been added
			String speciesID = IDs.getSpecieID(chs.getSpecieId());
			if (document.getElement(speciesID)==null){
				document.addElement(
						SpeciesBuilder.getKidaSpecies(chs.getSpecie(),request));
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
