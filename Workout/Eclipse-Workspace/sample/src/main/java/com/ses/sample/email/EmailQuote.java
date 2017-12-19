package com.ses.sample.email;

import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONException;
import org.json.JSONObject;

import alignmentquote.AlignmentPricingQuote;
import batteryquote.BatteryQuote;
import batteryquote.GenericVehicle;
import tirequote.Store;
import tirequote.Tire;
import tirequote.TireQuote;

public class EmailQuote {

    private static MailMessage mailMessage;
    public static final String TIRE_REPLACEMENT_CHOICE_ID = "2745";
	public static final String MVAN_TIRE_REPLACEMENT_CHOICE_ID = "2782";
	public static final String BATTERY_CHOICE_ID = "2750";
	public static final String ALIGNMENT_CHOICE_ID = "2749";
    
    public static MailMessage getInstance(String siteName) {
		if (siteName.equalsIgnoreCase("TP")) {
			return new TPMailMessage();
		} else if (siteName.equalsIgnoreCase("WWT")) {
			return new WWTMailMessage();
		} else if (siteName.equalsIgnoreCase("HT")) {
			return new HTMailMessage();
		}
		return new FCACMailMessage();
	}
    
    public static String getWebSiteAppRoot(String siteName) {
    	String appRoot = "";
    	if (!StringUtils.isNullOrEmpty(siteName)) {
			appRoot = "http://";
			
//			if (ServerUtil.isProduction()) {
//				appRoot += "www.";
//			} else if (ServerUtil.isBSROQa()) {
//				appRoot += "cwh-qa.";
//			} else {
//				appRoot += "cwh-uat.";
//			}
			appRoot += "cwh-uat.";
			
	    	if ("FCAC".equalsIgnoreCase(siteName)) {
				appRoot += "firestonecompleteautocare.com";
			} else if ("TP".equalsIgnoreCase(siteName)) {
				appRoot += "tiresplus.com";
			} else if ("HT".equalsIgnoreCase(siteName)) {
				appRoot += "hibdontire.com";
			} else if ("WWT".equalsIgnoreCase(siteName)) {
				appRoot += "wheelworks.net";
			}
    	}
    	return appRoot;
    }
    
	public static void main(String[] args) {
		System.out.println("Test!");
		String quoteId = "187";
		String siteName = "TP";
		String source = "battery";
		String firstName = "Aravindhan";
		String lastName = "Jayakumar";
		String email = "hari6589@gmail.com";
		
		String messageContent = "";
		Mailer mailer = new Mailer();
		Quote quote = new Quote();
		try {
			Store store = null;
			if(source.equals("tire")) {
				store = quote.getStoreDetail("11940");
				TireQuote tireQuote = quote.getTireQuote(quoteId);
				messageContent = generateTireQuoteMessage(tireQuote, store, siteName, firstName, lastName, email);
			} else if(source.equals("battery")) {
				store = quote.getStoreDetail("11940");
				BatteryQuote batteryQuote = quote.getBatteryQuote(quoteId);
				messageContent = generateBatteryQuoteMessage(batteryQuote, store, siteName, firstName, lastName, email);
			} else if(source.equals("alignment")) {
				store = quote.getStoreDetail("11940");
				AlignmentPricingQuote alignmentQuote = quote.getAlignmentPricingQuote(quoteId);
				messageContent = generateAlignmentQuoteMessage(alignmentQuote, store, siteName, firstName, lastName, email);
			}
			
			//mailer.sendMail(messageContent);
			
			// Temp HTML Viewer section
			StringBuilder htmlBuilder = new StringBuilder();
			htmlBuilder.append("<html><body>");
			htmlBuilder.append(messageContent);
			htmlBuilder.append("</body></html>\n");
			System.out.println(System.getProperty("user.home"));
			FileWriter writer = new FileWriter(System.getProperty("user.home") + "/test.html");
			writer.write(htmlBuilder.toString());
			writer.close();
			
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static String generateTireQuoteMessage(TireQuote tireQuote, Store store, String siteName, String firstName, String lastName, String email) throws UnsupportedEncodingException {
		
		MailMessage mailMessage = getInstance(siteName);
		String messageTemplate = mailMessage.getTireQuoteMailMessage();
		
		Map<String,String> data = new LinkedHashMap<String,String>();
		Map<String,String> staticData = new LinkedHashMap<String,String>();
		
		String customerName = "";
		String baseURL = getWebSiteAppRoot(siteName); //Need to be dynamic based on siteName and Environment(www/chw-qa/cwh-uat)
		String appointmentURL = baseURL
				+ "/appointment/index.htm?storeNumber="
				+ store.getStoreNumber()
				+ "&amp;pricing=false&amp;maintenanceChoices="
				+ TIRE_REPLACEMENT_CHOICE_ID //if(MVAN) 2782 else 2745 // maintenanceChoice
				+ "&amp;appointment.tireQuoteId="
				+ java.net.URLEncoder.encode(tireQuote.getTireQuoteId().toString(), "utf-8")
				+ "&amp;nav=tire&amp;appointment.comments="
				+ java.net.URLEncoder.encode(getAppointmentComments(tireQuote.getTire(), tireQuote), "utf-8");
		
		if(siteName.toLowerCase().contains("fcac")) {
			appointmentURL+= "&amp;lw_cmp=int-em_fcac_eg-fcac-tires-other_em-link";
		}
		if(siteName.toLowerCase().contains("tp")) {
			appointmentURL+= "&amp;lw_cmp=int-em_tp_eg-tp-tires-other_em-link";
		}
		if(siteName.toLowerCase().contains("ht")) {
			appointmentURL+= "&amp;lw_cmp=int-em_hib_eg-hib-tires-other_em-link";
		}
		if(siteName.toLowerCase().contains("wwt")) {
			appointmentURL+= "&amp;lw_cmp=int-em_ww_eg-ww-tires-other_em-link";
		}
		
		if (!StringUtils.isNullOrEmpty(firstName)) {
			customerName = firstName + " ";
		}
		if (!StringUtils.isNullOrEmpty(lastName)) {
			customerName += lastName;
		}
		
		if (tireQuote.getVehicleFitment() != null) {
			data.put("USER_VEHILE", tireQuote.getVehicleFitment().getYear() + " " +tireQuote.getVehicleFitment().getMake() + " " + tireQuote.getVehicleFitment().getModel() + " " + tireQuote.getVehicleFitment().getSubmodel());
			appointmentURL += "&amp;vehicle.year="
					+ tireQuote.getVehicleFitment().getYear()
					+ "&amp;vehicle.make="
					+ java.net.URLEncoder.encode(tireQuote.getVehicleFitment().getMake(), "utf-8")
					+ "&amp;vehicle.model="
					+ java.net.URLEncoder.encode(tireQuote.getVehicleFitment().getModel(), "utf-8")
					+ "&amp;vehicle.submodel="
					+ java.net.URLEncoder.encode(tireQuote.getVehicleFitment().getSubmodel(), "utf-8");
		} else {
			data.put("USER_VEHILE", tireQuote.getTireSize().getCrossSection()+"/"+tireQuote.getTireSize().getAspectRation()+"/"+tireQuote.getTireSize().getRimSize()+" Tires");				
		}
		
		data.put("QUOTE_URL", mailMessage.getTireQuoteURL());
		data.put("IMAGE_PATH", mailMessage.getImagePath());
		data.put("CUSTOMER_NAME", (!StringUtils.isNullOrEmpty(customerName)) ? customerName : "");
		data.put("PUNCHMARK", (!StringUtils.isNullOrEmpty(customerName)) ? "&#8217;s" : "");
		data.put("CUSTOMER_FIRST_NAME", firstName);
		data.put("CUSTOMER_LAST_NAME", lastName);
		data.put("CURRENT_DATE", (new SimpleDateFormat("MMMM d, yyyy")).format(new Date()));
		data.put("APPOINTMENT_URL", appointmentURL);
		data.put("BASE_URL", baseURL);
		
		// Map Store Data
		/// mapStoreDetails(store, data);
		System.out.println("before Store section...");
		data.put("STORE_NAME", store.getStoreName());
		System.out.println("1");
		data.put("STORE_NUMBER", StringUtils.padStoreNumber(String.valueOf(store.getStoreNumber())));
		data.put("STORE_ADDRESS", store.getAddress());
		data.put("STORE_CITY", store.getCity());
		data.put("STORE_STATE", store.getState());
		data.put("STORE_ZIP", store.getZip());
		data.put("STORE_PHONE", store.getPhone());
		//data.put("STORE_HOUR", ((store.getStoreHour() != null) ? store.getStoreHour() : ""));
		//data.put("STORE_DETAIL_URL", mailMessage.getStoreDetailURL(statesMap, store)); // yet to be done
		data.put("STORE_HOLIDAY", "");	//Todo
		data.put("STORE_LAT", store.getLatitude().toString());
		data.put("STORE_LNG", store.getLongitude().toString());
		data.put("BING_MAPS_KEY", mailMessage.getBingMapsKey());
		
		// Map Tire Quote Detail
		/// mapTireQuoteDetails(tire, rearTire, quote, quoteItem, baseURL, data);
		DecimalFormat mf = new DecimalFormat("###,###");
		DecimalFormat decimalValueFormatter = new DecimalFormat("##.00");
		data.put("QUOTE_ID", tireQuote.getTireQuoteId().toString());
		if (tireQuote.getRearTire() != null) {
			data.put("TIRE_SIZE_INFO", "Front Size: " + tireQuote.getTire().getTireSize() + " | Rear Size: " + tireQuote.getRearTire().getTireSize());
			data.put("ARTICLE_NO", tireQuote.getTire().getArticle() + ";" + tireQuote.getRearTire().getArticle());
		} else {
			data.put("TIRE_SIZE_INFO", "Size: " + tireQuote.getTire().getTireSize());
			data.put("ARTICLE_NO", tireQuote.getTire().getArticle().toString());
		}
		
		data.put("TIRE_SIZE", "Size: "+tireQuote.getTire().getTireSize());
		data.put("SPEED_RATING", tireQuote.getTire().getSpeedRating());
		data.put("SPEED_RATING_MPH", tireQuote.getTire().getSpeedRatingMPH());
		data.put("LOAD_RANGE", (tireQuote.getTire().getLoadRange() != null) ? tireQuote.getTire().getLoadRange() : "");
		data.put("LOAD_INDEX", (tireQuote.getTire().getLoadIndex() != null) ? tireQuote.getTire().getLoadIndex().toString() : "");
		data.put("LOAD_INDEX_POUNDS", (tireQuote.getTire().getLoadIndexPounds() != null) ? tireQuote.getTire().getLoadIndexPounds().toString() : "");
		data.put("SIDE_WALL", tireQuote.getTire().getSidewallDescription());
		data.put("MILEAGE", (tireQuote.getTire().getMileage() != null) ? mf.format(Integer.parseInt(tireQuote.getTire().getMileage().toString())) : "No");
		data.put("TIRE_BRAND", tireQuote.getTire().getBrand());
		data.put("TIRE_NAME", tireQuote.getTire().getTireName());
		data.put("TIRE_TYPE", tireQuote.getTire().getTireType());
		data.put("STD_OPT", ("O".equalsIgnoreCase(tireQuote.getTire().getStandardOptional())) ? "Optional" : "Standard");
		data.put("PROMO_DISPLAY_NAME", (tireQuote.getTirePromotion() != null) ? tireQuote.getTirePromotion().getPromoDisplayName() : "");
		data.put("PROMO_URL", (tireQuote.getTirePromotion() != null) ? mailMessage.getTirePromoURL() : "");
		
		if (tireQuote.getTirePromotion() != null) {
			if (tireQuote.getTirePromotion().getPromoId() != null) {
				// if (SiteTypes.TP.getSiteType().equalsIgnoreCase(getSiteName())) { /// Check
				if(true) {
					data.put("TIRE_PROMO_ID", tireQuote.getTirePromotion().getPromoId().toString());
				} else {
					data.put("TIRE_PROMO_ID", tireQuote.getTirePromotion().getPromoName());
				}
			}
		}
		
		data.put("TIRE_PROMO_DISCOUNT_AMOUNT", (tireQuote.getQuoteItem().getDiscount() > 0) ? String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getDiscount())) : "");
		data.put("TIRE_QTY", tireQuote.getQuantity().toString());
		data.put("TIRE_UNIT_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getUnitPrice())));
		data.put("TIRE_TOTAL_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getTotalTirePrice())));
		if (tireQuote.getRearTire() != null) {
			data.put("REAR_TIRE_QTY", tireQuote.getRearQuantity().toString());
			if (tireQuote.getQuoteItem().getRearUnitPrice() > 0) {
				data.put("REAR_TIRE_UNIT_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getRearUnitPrice())));
				data.put("REAR_TIRE_TOTAL_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getRearTotalTirePrice())));
			} else {
				data.put("REAR_TIRE_UNIT_PRICE", "");
				data.put("REAR_TIRE_TOTAL_PRICE", "");
			}
		}
		
		if (tireQuote.getRearTire() != null) {
			data.put("SUBTOTAL_PRICE", decimalValueFormatter.format((tireQuote.getQuoteItem().getTotalTirePrice() + tireQuote.getQuoteItem().getRearTotalTirePrice()) - tireQuote.getQuoteItem().getDiscount()));
		} else {
			data.put("SUBTOTAL_PRICE", decimalValueFormatter.format(tireQuote.getQuoteItem().getTotalTirePrice() - tireQuote.getQuoteItem().getDiscount()));
		}
		data.put("WHEEL_BALANCE_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getWheelBalance())));
		if (tireQuote.getQuoteItem().getTpmsValveServiceKit() > 0) {
			data.put("TPMS_KIT_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getTpmsValveServiceKit())));
			data.put("TPMS_KIT_LABOR_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getTpmsValveServiceKitLabor())));
			data.put("VALVE_STEM_PRICE", "");
		} else {
			data.put("VALVE_STEM_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getValveStem())));
			data.put("TPMS_KIT_PRICE", "");
			data.put("TPMS_KIT_LABOR_PRICE", "");
		}
		data.put("STATE_ENVIRONMENT_FEE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getStateEnvironmentalFee())));
		data.put("SCRAP_TIRE_RECYCLE_FEE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getScrapTireRecyclingCharge())));
		data.put("SHOP_SUPPLY_FEE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getShopSupplies())));
		data.put("TAX", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getTax())));
		data.put("SUB_TOTAL_PRICE", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getTotal())));
		data.put("TOTAL", String.valueOf(decimalValueFormatter.format(tireQuote.getQuoteItem().getTotal())));
		data.put("COPY_RIGHT_YEAR", new SimpleDateFormat("yyyy").format(new java.util.Date()));
		
		///mapTireQuoteHTML(customerName, staticdata);
		staticData.put("BRAND_BANNER_IMAGE", mailMessage.getBannerHTML());
		staticData.put("PREFERED_VEHILE", (tireQuote.getVehicleFitment() != null) ? mailMessage.getVehicleDetailsHTML() : "");
		staticData.put("PREFERED_STORE", mailMessage.getStoreDetailsHTML());
		staticData.put("STORE_BING_MAP", mailMessage.getBingStoreMapHtml());
		staticData.put("TIRE_SPEC_INFO", mailMessage.getTireSpecDetailsHTML());
		staticData.put("QUOTE_HEADER", mailMessage.getTireQuoteHeaderHTML());
		staticData.put("CUSTOMER_INFO", (!StringUtils.isNullOrEmpty(customerName)) ? mailMessage.getCustomerInfoHTML() : "");
		staticData.put("QUOTE_TIRE_DETAIL_INFO", mailMessage.getTireDetailsHTML());
		staticData.put("QUOTE_LINE_ITEMS", mailMessage.getQuoteLineItemsHTML());
		staticData.put("QUOTE_SERVICE_GUARANTY", mailMessage.getQuoteServiceGuaranty());
		staticData.put("CFNA_HTML", mailMessage.getCfnaHtml());
		if (tireQuote.getRearTire() != null) {
			staticData.put("QUOTE_TIRE_QTY_PRICE", "");
			staticData.put("QUOTE_MATCHED_SET_TIRE_PRICES", mailMessage.getMatchedSetTirePriceHTML());
			if (tireQuote.getQuoteItem().getUnitPrice() > 0) {
				staticData.put("FRONT_TIRE_PRICE", mailMessage.getFrontTirePriceHTML());
			} else {
				staticData.put("FRONT_TIRE_PRICE", mailMessage.getContactStorePricingHTML());
			}				
			if (tireQuote.getQuoteItem().getRearUnitPrice() > 0) {
				staticData.put("REAR_TIRE_PRICE", mailMessage.getRearTirePriceHTML());
			} else {
				staticData.put("REAR_TIRE_PRICE", mailMessage.getContactStorePricingHTML());
			}				
		} else {
			staticData.put("QUOTE_TIRE_QTY_PRICE", mailMessage.getTirePriceHTML());
			staticData.put("QUOTE_MATCHED_SET_TIRE_PRICES", "");
		}
		staticData.put("QUOTE_TIRE_DISCOUNTS", (tireQuote.getQuoteItem().getDiscount() > 0) ? mailMessage.getTirePromotionHTML() : "");
		staticData.put("QUOTE_TIRE_SUBTOTAL", mailMessage.getTireSubTotalHTML());
		staticData.put("QUOTE_WHEEL_BALANCE_PRICE", (tireQuote.getQuoteItem().getWheelBalance() > 0) ? mailMessage.getTireWheelBalanceHTML() : "");
		staticData.put("QUOTE_TIREMOUNTING_PRICE", mailMessage.getTireMountingHTML());
		staticData.put("QUOTE_WHEEL_ALIGNMENT_CHECK_PRICE", mailMessage.getWheelAlignmentHTML());
		staticData.put("QUOTE_TPMS_KIT_PRICE", (tireQuote.getQuoteItem().getTpmsValveServiceKit() > 0) ? mailMessage.getTPMSValveServiceKitHTML() : "");
		staticData.put("QUOTE_TPMS_KIT_LABOR_PRICE", (tireQuote.getQuoteItem().getTpmsValveServiceKitLabor() > 0) ? mailMessage.getTPMSValveServiceKitLaborHTML() : "");
		staticData.put("QUOTE_VALVE_STEM_PRICE", (tireQuote.getQuoteItem().getValveStem() > 0) ? mailMessage.getValveStemPriceHTML() : "");
		staticData.put("QUOTE_STATE_ENVIRONMENT_FEE_PRICE", (tireQuote.getQuoteItem().getStateEnvironmentalFee() > 0) ? mailMessage.getStateEnvironmentFeeHTML() : "");
		staticData.put("QUOTE_SCRAP_TIRE_RECYCLING_CHARGE_PRICE", (tireQuote.getQuoteItem().getScrapTireRecyclingCharge() > 0) ? mailMessage.getScrapTireRecyclingChargeHTML() : "");
		staticData.put("QUOTE_SHOP_SUPPLIES_PRICE", (tireQuote.getQuoteItem().getShopSupplies() > 0) ? mailMessage.getShopSuppliesHTML() : "");
		staticData.put("QUOTE_TAX_AMOUNT", (tireQuote.getQuoteItem().getTax() > 0) ? mailMessage.getTaxHTML() : "");
		staticData.put("QUOTE_SUBTOTAL_PRICE", (tireQuote.getQuoteItem().getTotal() > 0) ? mailMessage.getTireQuoteSubTotalHTML() : "");
		staticData.put("QUOTE_TOTAL_PRICE", (tireQuote.getQuoteItem().getTotal() > 0) ? mailMessage.getTireQuoteTotalHTML() : "");
		staticData.put("QUOTE_DISCLAIMER", mailMessage.getTireQuoteDisclaimerHTML());
		staticData.put("QUOTE_FOOTER", mailMessage.getQuoteFooterHTML());
		
		String mailContent = StringUtils.populateEmailMessageContent(messageTemplate, staticData);
		mailContent = StringUtils.populateEmailMessageContent(mailContent, data);
		
		return mailContent;
	}
		
	private static String generateBatteryQuoteMessage(BatteryQuote batteryQuote, Store store, String siteName, String firstName, String lastName, String email) throws UnsupportedEncodingException {
		MailMessage mailMessage = getInstance(siteName);
		String messageTemplate = mailMessage.getBatteryQuoteMailMessage();
		
		Map<String,String> data = new LinkedHashMap<String,String>();
		Map<String,String> staticData = new LinkedHashMap<String,String>();
		
		String customerName = "";
		String baseURL = getWebSiteAppRoot(siteName); //Need to be dynamic based on siteName and Environment(www/chw-qa/cwh-uat)
		
		String appointmentURL = baseURL
				+ "/appointment/index.htm?storeNumber="
				+ store.getStoreNumber()
				+ "&amp;pricing=false&amp;maintenanceChoices="
				+ BATTERY_CHOICE_ID
				+ "&amp;appointment.batteryQuoteId="
				+ java.net.URLEncoder.encode(String.valueOf(batteryQuote.getBatteryQuoteId()), "utf-8")
				+ "&amp;nav=battery&amp;appointment.comments="
				+ java.net.URLEncoder.encode(getAppointmentComments(batteryQuote), "utf-8");

		if(siteName.toLowerCase().contains("fcac")){
			appointmentURL+= "&amp;lw_cmp=int-em_fcac_eg-fcac-btry-other_em-link";
		}
		if(siteName.toLowerCase().contains("tp")){
			appointmentURL+= "&amp;lw_cmp=int-em_tp_eg-tp-btry-other_em-link";
		}
		if(siteName.toLowerCase().contains("ht")){
			appointmentURL+= "&amp;lw_cmp=int-em_hib_eg-hib-btry-other_em-link";
		}
		if(siteName.toLowerCase().contains("wwt")){
			appointmentURL+= "&amp;lw_cmp=int-em_ww_eg-ww-btry-other_em-link";
		}
		
		if (!StringUtils.isNullOrEmpty(firstName)) {
			customerName = firstName + " ";
		}
		if (!StringUtils.isNullOrEmpty(lastName)) {
			customerName += lastName;
		}
		
		if (batteryQuote.getVehicle() != null) {
			data.put("USER_VEHILE", batteryQuote.getVehicle().getYear() + " " +batteryQuote.getVehicle().getMake() + " " + batteryQuote.getVehicle().getModel() + " " + batteryQuote.getVehicle().getEngine());
			appointmentURL += "&amp;vehicle.year="
					+ batteryQuote.getVehicle().getYear()
					+ "&amp;vehicle.make="
					+ batteryQuote.getVehicle().getMake()
					+ "&amp;vehicle.model="
					+ batteryQuote.getVehicle().getModel()
					+ "&amp;vehicle.submodel="
					+ batteryQuote.getVehicle().getSubmodel();
		}
		System.out.println("1");
		data.put("QUOTE_URL", mailMessage.getBatteryQuoteURL());
		data.put("BASE_URL", baseURL);
		data.put("IMAGE_PATH", mailMessage.getImagePath());
		data.put("CUSTOMER_NAME", (!StringUtils.isNullOrEmpty(customerName)) ? customerName : "");
		data.put("PUNCHMARK", (!StringUtils.isNullOrEmpty(customerName)) ? "&#8217;s" : "");
		data.put("CUSTOMER_FIRST_NAME", firstName);
		data.put("CUSTOMER_LAST_NAME", lastName);
		data.put("CURRENT_DATE", (new SimpleDateFormat("MMMM d, yyyy")).format(new Date()));
		data.put("APPOINTMENT_URL", appointmentURL);
		
		/// mapStoreDetails(store, data);
		data.put("STORE_NAME", store.getStoreName());
		data.put("STORE_NUMBER", StringUtils.padStoreNumber(String.valueOf(store.getStoreNumber())));
		data.put("STORE_ADDRESS", store.getAddress());
		data.put("STORE_CITY", store.getCity());
		data.put("STORE_STATE", store.getState());
		data.put("STORE_ZIP", store.getZip());
		data.put("STORE_PHONE", store.getPhone());
		//data.put("STORE_HOUR", ((store.getStoreHour() != null) ? store.getStoreHour() : ""));
		//data.put("STORE_DETAIL_URL", mailMessage.getStoreDetailURL(statesMap, store)); // yet to be done
		data.put("STORE_HOLIDAY", "");	//Todo
		data.put("STORE_LAT", store.getLatitude().toString());
		data.put("STORE_LNG", store.getLongitude().toString());
		data.put("BING_MAPS_KEY", mailMessage.getBingMapsKey());
		
		/// mapBatteryQuoteDetails(batteryQuote, data);
		data.put("QUOTE_ID", String.valueOf(batteryQuote.getBatteryQuoteId()));
		data.put("BATTERY_DESCRIPTION", batteryQuote.getBattery().getProductName());
		data.put("BATTERY_WARRANTY_YEARS", batteryQuote.getBattery().getPerformanceWarrantyYearsOrMonths());
		data.put("BATTERY_ARTICLE_NO", batteryQuote.getBattery().getPartNumber().toString());
		data.put("BATTERY_PRICE", batteryQuote.getBattery().getWebPrice().toString());
		data.put("BATTERY_INSTALLATION_PRICE", batteryQuote.getBattery().getInstallationAmount().toString());
		data.put("QUOTE_SUBTOTAL_PRICE", batteryQuote.getSubtotal().toString());
		data.put("QUOTE_TOTAL_PRICE", batteryQuote.getTotal().toString());
		data.put("COPY_RIGHT_YEAR", new SimpleDateFormat("yyyy").format(new java.util.Date()));
		
		/// mapBatteryQuoteHTML(customerName, staticdata);
		staticData.put("BRAND_BANNER_IMAGE", mailMessage.getBannerHTML());
		staticData.put("PREFERED_VEHILE", mailMessage.getVehicleDetailsHTML());
		staticData.put("PREFERED_STORE", mailMessage.getStoreDetailsHTML());
		staticData.put("STORE_BING_MAP", mailMessage.getBingStoreMapHtml());
		staticData.put("CFNA_HTML", mailMessage.getCfnaHtml());
		staticData.put("TIRE_SPEC_INFO", "");
		staticData.put("QUOTE_HEADER_HTML", mailMessage.getBatteryQuoteHeaderHTML());
		staticData.put("QUOTE_DETAILS_HTML", mailMessage.getBatteryQuoteDetailsHTML());
		staticData.put("QUOTE_PRICE_DISCLAIMER", mailMessage.getBatteryQuoteDisclaimerHTML());
		staticData.put("QUOTE_APPOINTMENT_HTML", mailMessage.getBatteryQuoteScheduleAppointmentHTML());
		staticData.put("QUOTE_FOOTER", mailMessage.getQuoteFooterHTML());
		
		String mailContent = StringUtils.populateEmailMessageContent(messageTemplate, staticData);
		mailContent = StringUtils.populateEmailMessageContent(mailContent, data);
		
		return mailContent;
	}
	
	public static String generateAlignmentQuoteMessage(AlignmentPricingQuote alignmentQuote, Store store, String siteName, String firstName, String lastName, String email) throws UnsupportedEncodingException {
		MailMessage mailMessage = getInstance(siteName);
		String messageTemplate = mailMessage.getAlignmentQuoteMailMessage();
		
		Map<String,String> data = new LinkedHashMap<String,String>();
		Map<String,String> staticData = new LinkedHashMap<String,String>();
		
		String customerName = "";
		String baseURL = getWebSiteAppRoot(siteName); //Need to be dynamic based on siteName and Environment(www/chw-qa/cwh-uat)
		String appointmentURL = baseURL
				+ "/appointment/index.htm?storeNumber="
				+ store.getStoreNumber()
				+ "&amp;maintenanceChoices="
				+ ALIGNMENT_CHOICE_ID
				+ "&amp;pricing=false&amp;nav=alignment"
				+ "&amp;appointment.comments="
				+ java.net.URLEncoder.encode(getAppointmentComments(alignmentQuote), "utf-8");
		if(siteName.toLowerCase().contains("fcac")){
			appointmentURL+= "&amp;lw_cmp=int-em_fcac_eg-fcac-align-other_em-link";
		}
		if(siteName.toLowerCase().contains("tp")){
			appointmentURL+= "&amp;lw_cmp=int-em_tp_eg-tp-align-other_em-link";
		}
		if(siteName.toLowerCase().contains("ht")){
			appointmentURL+= "&amp;lw_cmp=int-em_hib_eg-hib-align-other_em-link";
		}
		if(siteName.toLowerCase().contains("wwt")){
			appointmentURL+= "&amp;lw_cmp=int-em_ww_eg-ww-align-other_em-link";
		}
		if (alignmentQuote.getVehicleModel() != null) {
			data.put("USER_VEHILE", alignmentQuote.getVehicleYear() + " " +alignmentQuote.getVehicleMake() + " " + alignmentQuote.getVehicleModel() + " " + alignmentQuote.getVehicleSubmodel());
			appointmentURL += "&amp;vehicle.year="
								+ alignmentQuote.getVehicleYear()
								+ "&amp;vehicle.make="
								+ java.net.URLEncoder.encode(alignmentQuote.getVehicleMake(), "utf-8")
								+ "&amp;vehicle.model=" 
								+ java.net.URLEncoder.encode(alignmentQuote.getVehicleModel(), "utf-8");
		}
		
		data.put("QUOTE_URL", mailMessage.getAlignmentQuoteURL());
		data.put("BASE_URL", baseURL);
		data.put("IMAGE_PATH", mailMessage.getImagePath());
		data.put("CUSTOMER_NAME", (!StringUtils.isNullOrEmpty(customerName)) ? customerName : "");
		data.put("PUNCHMARK", (!StringUtils.isNullOrEmpty(customerName)) ? "&#8217;s" : "");
		data.put("CUSTOMER_FIRST_NAME", firstName);
		data.put("CUSTOMER_LAST_NAME", lastName);
		data.put("CURRENT_DATE", (new SimpleDateFormat("MMMM d, yyyy")).format(new Date()));
		data.put("APPOINTMENT_URL", appointmentURL);
		
		/// mapStoreDetails(store, data);
		data.put("STORE_NAME", store.getStoreName());
		data.put("STORE_NUMBER", StringUtils.padStoreNumber(String.valueOf(store.getStoreNumber())));
		data.put("STORE_ADDRESS", store.getAddress());
		data.put("STORE_CITY", store.getCity());
		data.put("STORE_STATE", store.getState());
		data.put("STORE_ZIP", store.getZip());
		data.put("STORE_PHONE", store.getPhone());
		//data.put("STORE_HOUR", ((store.getStoreHour() != null) ? store.getStoreHour() : ""));
		//data.put("STORE_DETAIL_URL", mailMessage.getStoreDetailURL(statesMap, store));  // yet to be done
		data.put("STORE_HOLIDAY", "");	//ToDO
		data.put("STORE_LAT", store.getLatitude().toString());
		data.put("STORE_LNG", store.getLongitude().toString());
		data.put("BING_MAPS_KEY", mailMessage.getBingMapsKey());
		
		/// mapAlignmentQuoteDetails(alignmentQuote, data);
		data.put("QUOTE_ID", String.valueOf(alignmentQuote.getAlignmentQuoteId()));
		data.put("ALIGNMENT_TYPE", getAlignmentType(alignmentQuote.getPricingName()));
		data.put("ALIGNMENT_CHECK_ARTICLE", mailMessage.getAlignmentCheckArticleNo());
		data.put("ALIGNMENT_TYPE_ARTICLE", alignmentQuote.getArticle().toString());
		data.put("QUOTE_SUBTOTAL_PRICE", alignmentQuote.getPrice().toString());
		data.put("QUOTE_TOTAL_PRICE", alignmentQuote.getPrice().toString());
		data.put("COPY_RIGHT_YEAR", new SimpleDateFormat("yyyy").format(new java.util.Date()));
		
		/// mapAlignmentQuoteHTML(customerName, staticdata);
		staticData.put("BRAND_BANNER_IMAGE", mailMessage.getBannerHTML());
		staticData.put("PREFERED_VEHILE", mailMessage.getVehicleDetailsHTML());
		staticData.put("PREFERED_STORE", mailMessage.getStoreDetailsHTML());
		staticData.put("STORE_BING_MAP", mailMessage.getBingStoreMapHtml());
		staticData.put("CFNA_HTML", mailMessage.getCfnaHtml());
		staticData.put("TIRE_SPEC_INFO", "");
		staticData.put("QUOTE_HEADER_HTML", mailMessage.getAlignmentQuoteHeaderHTML());
		staticData.put("QUOTE_APPOINTMENT_HTML", mailMessage.getAlignmentQuoteScheduleAppointmentHTML());
		staticData.put("QUOTE_DETAILS_HTML", mailMessage.getAlignmentQuoteDetailsHTML());
		staticData.put("QUOTE_PRICE_DISCLAIMER", mailMessage.getAlignmentQuoteDisclaimerHTML());
		staticData.put("QUOTE_FOOTER", mailMessage.getQuoteFooterHTML());
		
		String mailContent = StringUtils.populateEmailMessageContent(messageTemplate, staticData);
		mailContent = StringUtils.populateEmailMessageContent(mailContent, data);
		
		return mailContent;
	}
	
	public static String getAppointmentComments(Tire tire, TireQuote quote) {
		String cmtArticle = String.valueOf(tire.getArticle());
        String cmtDsc = tire.getBrand() + " " + tire.getTireName();
        String cmtSize = tire.getTireSize();
        String cmtPrice = "$" + tire.getRetailPrice();
        
		String comments = "tire quote id:  " + quote.getTireQuoteId()
                + ",  tire article number:  " + cmtArticle
                + ",  tire description: " + cmtDsc
                + ",  tire size: " + cmtSize
                + ",  tire quantity: " + quote.getQuantity()
                + ",  tire unit price: " + cmtPrice
                + ",  tire quote End;";
		
		return comments;
	}
	
	public static String getAppointmentComments(BatteryQuote quote) {
		java.text.DecimalFormat decimalFormatter = new java.text.DecimalFormat("000000");
		String comments = "battery quote id:  " 
				+ decimalFormatter.format(quote.getBatteryQuoteId()) 
				+ ",  battery article number:  " 
				+ quote.getBattery().getPartNumber() 
				+ ",  battery description: " 
				+ quote.getBattery().getProductName() 
				+ ",  battery warranty: " 
				+ quote.getBattery().getTotalWarrantyMonths() 
				+ ",  battery unit price: $" 
				+ quote.getBattery().getWebPrice() 
				+ ",  battery quote End;";
		return comments;
	}
	
	public static String getAppointmentComments(AlignmentPricingQuote quote) {
		String comments = "alignment quote id:  " + quote.getAlignmentQuoteId()
	            + ",  alignment article number:  " + quote.getArticle()
	            + ",  alignment description: " + quote.getPricingName()
	            + ",  alignment unit price: " + quote.getPrice()
	            + ",  alignment quote End;";
		return comments;
	}
	
	public static String getAlignmentType(String type) {
		String alType = "";
		if (type.startsWith("Standard")) {
			alType="Standard";
		} else if (type.startsWith("Three")) {
			alType="Three Year";
		} else if(type.startsWith("Life")) {
			alType="Life Time";
		}
		return alType;
	}
}
