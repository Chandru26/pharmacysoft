package com.deemsoft.pharmacysoft.service;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deemsoft.pharmacysoft.model.Address;
import com.deemsoft.pharmacysoft.model.Contacts;
import com.deemsoft.pharmacysoft.model.Catalogs;
import com.deemsoft.pharmacysoft.model.Accounts;
import com.deemsoft.pharmacysoft.model.AccountTransactions;
import com.deemsoft.pharmacysoft.model.Invoices;
import com.deemsoft.pharmacysoft.model.InvoicesItems;
import com.deemsoft.pharmacysoft.model.Purchases;
import com.deemsoft.pharmacysoft.model.PurchasesItems;
import com.deemsoft.pharmacysoft.model.Payments;
import com.deemsoft.pharmacysoft.model.Returnstock;
import com.deemsoft.pharmacysoft.model.ReturnstockItems;
import com.deemsoft.pharmacysoft.model.Shipments;
import com.deemsoft.pharmacysoft.model.Settings;


import com.deemsoft.pharmacysoft.dao.AddressDao;
import com.deemsoft.pharmacysoft.dao.ContactsDao;
import com.deemsoft.pharmacysoft.dao.CatalogsDao;
import com.deemsoft.pharmacysoft.dao.AccountsDao;
import com.deemsoft.pharmacysoft.dao.AccountTransactionsDao;
import com.deemsoft.pharmacysoft.dao.InvoicesDao;
import com.deemsoft.pharmacysoft.dao.InvoicesItemsDao;
import com.deemsoft.pharmacysoft.dao.PurchasesDao;
import com.deemsoft.pharmacysoft.dao.PurchasesItemsDao;
import com.deemsoft.pharmacysoft.dao.PaymentsDao;
import com.deemsoft.pharmacysoft.dao.ReturnstockDao;
import com.deemsoft.pharmacysoft.dao.ReturnstockItemsDao;
import com.deemsoft.pharmacysoft.dao.ShipmentsDao;
import com.deemsoft.pharmacysoft.dao.SettingsDao;


@Service("dbService")
@Transactional
public class DBServiceImpl implements DBService{

	//Address Service
	@Autowired
	private AddressDao addressDao;
	public void saveAddress(Address address){
		addressDao.save(address);
	}
	public List<Address> findAllAddress(){
		return addressDao.findAllAddress();
	}
	public Address findAddressByID(int id){
		return addressDao.findAddressByID(id);
	}
	public List<Address> searchAddress(Address address){
		return addressDao.searchAddress(address);
	}
	public List findAddressByPeriod(Period pd){
		return addressDao.findAddressByPeriod(pd);
	}
	public List searchAddressByName(String str){
		return addressDao.searchAddressByName(str);
	}
	public List getMaxAddressID(){
		return addressDao.getMaxID();
	}

	//Contacts Service
	@Autowired
	private ContactsDao contactsDao;
	public void saveContacts(Contacts contacts){
		contactsDao.save(contacts);
	}
	public List<Contacts> findAllContacts(){
		return contactsDao.findAllContacts();
	}
	public Contacts findContactsByID(int id){
		return contactsDao.findContactsByID(id);
	}
	public List<Contacts> searchContacts(Contacts contacts){
		return contactsDao.searchContacts(contacts);
	}
	public List findContactsByPeriod(Period pd){
		return contactsDao.findContactsByPeriod(pd);
	}
	public List searchContactsByName(String str){
		return contactsDao.searchContactsByName(str);
	}
	
	public List getMaxContactsID(){
		return contactsDao.getMaxID();
	}

	//Catalogs Service
	@Autowired
	private CatalogsDao catalogsDao;
	public void saveCatalogs(Catalogs catalogs){
		catalogsDao.save(catalogs);
	}
	public List<Catalogs> findAllCatalogs(){
		return catalogsDao.findAllCatalogs();
	}
	public Catalogs findCatalogsByID(int id){
		return catalogsDao.findCatalogsByID(id);
	}
	public List<Catalogs> searchCatalogs(Catalogs catalogs){
		return catalogsDao.searchCatalogs(catalogs);
	}
	public List findCatalogsByPeriod(Period pd){
		return catalogsDao.findCatalogsByPeriod(pd);
	}
	public List findCatalogsBarByPeriod(Period pd){
		return catalogsDao.findCatalogsBarByPeriod(pd);
	}
	public List searchCatalogsByName(String str){
		return catalogsDao.searchCatalogsByName(str);
	}
	public List searchCatalogsByBarcode(String str){
		return catalogsDao.searchCatalogsByBarcode(str);
	}
	public List getMaxCatalogsID(){
		return catalogsDao.getMaxID();
	}

	//Accounts Service
	@Autowired
	private AccountsDao accountsDao;
	public void saveAccounts(Accounts accounts){
		accountsDao.save(accounts);
	}
	public List<Accounts> findAllAccounts(){
		return accountsDao.findAllAccounts();
	}
	public Accounts findAccountsByID(int id){
		return accountsDao.findAccountsByID(id);
	}
	public List<Accounts> searchAccounts(Accounts accounts){
		return accountsDao.searchAccounts(accounts);
	}
	public List findAccountsByPeriod(Period pd){
		return accountsDao.findAccountsByPeriod(pd);
	}
	public List searchAccountsByName(String str){
		return accountsDao.searchAccountsByName(str);
	}
	public List getMaxAccountsID(){
		return accountsDao.getMaxID();
	}

	//AccountTransactions Service
	@Autowired
	private AccountTransactionsDao accounttransactionsDao;
	public void saveAccountTransactions(AccountTransactions accounttransactions){
		accounttransactionsDao.save(accounttransactions);
	}
	public List<AccountTransactions> findAllAccountTransactions(){
		return accounttransactionsDao.findAllAccountTransactions();
	}
	public AccountTransactions findAccountTransactionsByID(int id){
		return accounttransactionsDao.findAccountTransactionsByID(id);
	}
	public List<AccountTransactions> searchAccountTransactions(AccountTransactions accounttransactions){
		return accounttransactionsDao.searchAccountTransactions(accounttransactions);
	}
	public List findAccountTransactionsByPeriod(Period pd){
		return accounttransactionsDao.findAccountTransactionsByPeriod(pd);
	}
	public List searchAccountTransactionsByName(String str){
		return accounttransactionsDao.searchAccountTransactionsByName(str);
	}
	public List getMaxAccountTransactionsID(){
		return accounttransactionsDao.getMaxID();
	}

	//Invoices Service
	@Autowired
	private InvoicesDao invoicesDao;
	public void saveInvoices(Invoices invoices){
		invoicesDao.save(invoices);
	}
	public List<Invoices> findAllInvoices(){
		return invoicesDao.findAllInvoices();
	}
	public Invoices findInvoicesByID(int id){
		return invoicesDao.findInvoicesByID(id);
	}
	public List<Invoices> searchInvoices(Invoices invoices){
		return invoicesDao.searchInvoices(invoices);
	}
	public List ListInvoicesByStatusAndUser(int usr){
		return invoicesDao.ListInvoicesByStatusAndUser(usr);
	}
	public List findInvoicesByPeriod(Period pd){
		return invoicesDao.findInvoicesByPeriod(pd);
	}
	public List findSalesByPeriod(Period pd){
		return invoicesDao.findSalesByPeriod(pd);
	}
	public List searchInvoicesByName(String str){
		return invoicesDao.searchInvoicesByName(str);
	}
	
	public List getMaxInvoicesID(){
		return invoicesDao.getMaxID();
	}

	//InvoicesItems Service
	@Autowired
	private InvoicesItemsDao invoicesitemsDao;
	public void saveInvoicesItems(InvoicesItems invoicesitems){
		invoicesitemsDao.save(invoicesitems);
	}
	public List<InvoicesItems> findAllInvoicesItems(){
		return invoicesitemsDao.findAllInvoicesItems();
	}
	public InvoicesItems findInvoicesItemsByID(int id){
		return invoicesitemsDao.findInvoicesItemsByID(id);
	}
	public List<InvoicesItems> searchInvoicesItems(InvoicesItems invoicesitems){
		return invoicesitemsDao.searchInvoicesItems(invoicesitems);
	}
	public List findInvoicesItemsByPeriod(Period pd){
		return invoicesitemsDao.findInvoicesItemsByPeriod(pd);
	}
	public List searchInvoicesItemsByName(String str){
		return invoicesitemsDao.searchInvoicesItemsByName(str);
	}
	public List getInvoicesItemsByInvoiceID(int id){
		return invoicesitemsDao.getInvoicesItemsByInvoiceID(id);
	}
	public List getMaxInvoicesItemsID(){
		return invoicesitemsDao.getMaxID();
	}

	//Purchases Service
	@Autowired
	private PurchasesDao purchasesDao;
	public void savePurchases(Purchases purchases){
		purchasesDao.save(purchases);
	}
	public List<Purchases> findAllPurchases(){
		return purchasesDao.findAllPurchases();
	}
	public Purchases findPurchasesByID(int id){
		return purchasesDao.findPurchasesByID(id);
	}
	public List<Purchases> searchPurchases(Purchases purchases){
		return purchasesDao.searchPurchases(purchases);
	}
	public List findPurchasesByPeriod(Period pd){
		return purchasesDao.findPurchasesByPeriod(pd);
	}
	
	public List searchPurchasesByName(String str){
		return purchasesDao.searchPurchasesByName(str);
	}
	public List getMaxPurchasesID(){
		return purchasesDao.getMaxID();
	}

	//PurchasesItems Service
	@Autowired
	private PurchasesItemsDao purchasesitemsDao;
	public void savePurchasesItems(PurchasesItems purchasesitems){
		purchasesitemsDao.save(purchasesitems);
	}
	public List<PurchasesItems> findAllPurchasesItems(){
		return purchasesitemsDao.findAllPurchasesItems();
	}
	public PurchasesItems findPurchasesItemsByID(int id){
		return purchasesitemsDao.findPurchasesItemsByID(id);
	}
	public List<PurchasesItems> searchPurchasesItems(PurchasesItems purchasesitems){
		return purchasesitemsDao.searchPurchasesItems(purchasesitems);
	}
	public List findPurchasesItemsByPeriod(Period pd){
		return purchasesitemsDao.findPurchasesItemsByPeriod(pd);
	}
	public List findStocksVerificationReport(Period pd){
		return purchasesitemsDao.findStocksVerificationReport(pd);
	}
	
	public List findFastmovingStocksReport(Period pd){
		return purchasesitemsDao.findFastmovingStocksReport(pd);
	}
	public List findLowStocksReport(Period pd){
		return purchasesitemsDao.findLowStocksReport(pd);
	}
	public List searchPurchasesItemsByName(String str){
		return purchasesitemsDao.searchPurchasesItemsByName(str);
	}
	public List getMaxPurchasesItemsID(){
		return purchasesitemsDao.getMaxID();
	}
	public List getPurchasesItemsbyPurchaseID(int id){
		return purchasesitemsDao.getPurchasesItemsbyPurchaseID(id);
	}

	//Payments Service
	@Autowired
	private PaymentsDao paymentsDao;
	public void savePayments(Payments payments){
		paymentsDao.save(payments);
	}
	public List<Payments> findAllPayments(){
		return paymentsDao.findAllPayments();
	}
	public Payments findPaymentsByID(int id){
		return paymentsDao.findPaymentsByID(id);
	}
	public List<Payments> searchPayments(Payments payments){
		return paymentsDao.searchPayments(payments);
	}
	public List findPaymentsByPeriod(Period pd){
		return paymentsDao.findPaymentsByPeriod(pd);
	}
	public List searchPaymentsByName(String str){
		return paymentsDao.searchPaymentsByName(str);
	}
	public List getMaxPaymentsID(){
		return paymentsDao.getMaxID();
	}
	
	//Returnstock Service
	@Autowired
	private ReturnstockDao returnstockDao;
	public void saveReturnstock(Returnstock returnstock){
		returnstockDao.save(returnstock);
	}
	public List<Returnstock> findAllReturnstock(){
		return returnstockDao.findAllReturnstock();
	}
	public Returnstock findReturnstockByID(int id){
		return returnstockDao.findReturnstockByID(id);
	}
	public List<Returnstock> searchReturnstock(Returnstock returnstock){
		return returnstockDao.searchReturnstock(returnstock);
	}
	public List ListReturnstockByStatusAndUser(int usr){
		return returnstockDao.ListReturnstockByStatusAndUser(usr);
	}
	public List findReturnstockByPeriod(Period pd){
		return returnstockDao.findReturnstockByPeriod(pd);
	}
	public List findReturnByPeriod(Period pd){
		return returnstockDao.findReturnByPeriod(pd);
	}
	public List searchReturnstockByName(String str){
		return returnstockDao.searchReturnstockByName(str);
	}
	
	public List getMaxReturnstockID(){
		return returnstockDao.getMaxID();
	}

	//ReturnstockItems Service
	@Autowired
	private ReturnstockItemsDao returnstockitemsDao;
	public void saveReturnstockItems(ReturnstockItems returnstockitems){
		returnstockitemsDao.save(returnstockitems);
	}
	public List<ReturnstockItems> findAllReturnstockItems(){
		return returnstockitemsDao.findAllReturnstockItems();
	}
	public ReturnstockItems findReturnstockItemsByID(int id){
		return returnstockitemsDao.findReturnstockItemsByID(id);
	}
	public List<ReturnstockItems> searchReturnstockItems(ReturnstockItems returnstockitems){
		return returnstockitemsDao.searchReturnstockItems(returnstockitems);
	}
	public List findReturnstockItemsByPeriod(Period pd){
		return returnstockitemsDao.findReturnstockItemsByPeriod(pd);
	}
	public List searchReturnstockItemsByName(String str){
		return returnstockitemsDao.searchReturnstockItemsByName(str);
	}
	public List getReturnstockItemsByReturnID(int id){
		return returnstockitemsDao.getReturnstockItemsByReturnID(id);
	}
	public List getMaxReturnstockItemsID(){
		return returnstockitemsDao.getMaxID();
	}

	//Shipments Service
	@Autowired
	private ShipmentsDao shipmentsDao;
	public void saveShipments(Shipments shipments){
		shipmentsDao.save(shipments);
	}
	public List<Shipments> findAllShipments(){
		return shipmentsDao.findAllShipments();
	}
	public Shipments findShipmentsByID(int id){
		return shipmentsDao.findShipmentsByID(id);
	}
	public List<Shipments> searchShipments(Shipments shipments){
		return shipmentsDao.searchShipments(shipments);
	}
	public List findShipmentsByPeriod(Period pd){
		return shipmentsDao.findShipmentsByPeriod(pd);
	}
	public List searchShipmentsByName(String str){
		return shipmentsDao.searchShipmentsByName(str);
	}
	public List getMaxShipmentsID(){
		return shipmentsDao.getMaxID();
	}

	//Settings Service
	@Autowired
	private SettingsDao settingsDao;
	public void saveSettings(Settings settings){
		settingsDao.save(settings);
	}
	public List<Settings> findAllSettings(){
		return settingsDao.findAllSettings();
	}
	public Settings findSettingsByID(int id){
		return settingsDao.findSettingsByID(id);
	}
	public List<Settings> searchSettings(Settings settings){
		return settingsDao.searchSettings(settings);
	}
	public List findSettingsByPeriod(Period pd){
		return settingsDao.findSettingsByPeriod(pd);
	}
	public List searchSettingsByName(String str){
		return settingsDao.searchSettingsByName(str);
	}
	public List getMaxSettingsID(){
		return settingsDao.getMaxID();
	}


}
