
/**
 *   
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 00:03 23-11-2018
 */
package uk.ac.ncl.tongzhou.enterprisemiddleware.travelagent;

/**
 * SystemType
 * 
 * 
 */
public enum BookingSystemType {
	FLIGHT_SYSTEM("http://api-deployment-csc8104-180275186.b9ad.pro-us-east-1.openshiftapps.com/api/"), 
	TAXI_SYSTEM("http://api-deployment-csc8104-180332308.b9ad.pro-us-east-1.openshiftapps.com/api/"), 
	HOTEL_SYSTEM("http://api-deployment-csc8104-160712894.b9ad.pro-us-east-1.openshiftapps.com/api/");

	private String baseUrl;

	private BookingSystemType(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Return the baseUrl.
	 *
	 * @return baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Set the value of baseUrl
	 *
	 * @param baseUrl:
	 *            baseUrl to be set.
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
