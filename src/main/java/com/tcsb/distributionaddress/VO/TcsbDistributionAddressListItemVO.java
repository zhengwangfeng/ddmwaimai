package com.tcsb.distributionaddress.VO;

import java.util.List;

import com.tcsb.distributionaddress.entity.TcsbDistributionAddressEntity;

public class TcsbDistributionAddressListItemVO {
	private List<TcsbDistributionAddressEntity> yDistributionList;
	private List<TcsbDistributionAddressEntity> nDistributionList;
	public List<TcsbDistributionAddressEntity> getyDistributionList() {
		return yDistributionList;
	}
	public void setyDistributionList(
			List<TcsbDistributionAddressEntity> yDistributionList) {
		this.yDistributionList = yDistributionList;
	}
	public List<TcsbDistributionAddressEntity> getnDistributionList() {
		return nDistributionList;
	}
	public void setnDistributionList(
			List<TcsbDistributionAddressEntity> nDistributionList) {
		this.nDistributionList = nDistributionList;
	}
}
