package com.deemsoft.pharmacysoft.service;

import java.util.List;
import com.deemsoft.pharmacysoft.model.Period;
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


public interface DBService {

	//Address Service
	public void saveAddress(Address address);
	public List <Address> findAllAddress();
	public Address findAddressByID(int id);
	public List<Address> searchAddress(Address address);
	public List findAddressByPeriod(Period pd);
	public List searchAddressByName(String str);
	public List getMaxAddressID();
	//Contacts Service
	public void saveContacts(Contacts contacts);
	public List <Contacts> findAllContacts();
	public Contacts findContactsByID(int id);
	public List<Contacts> searchContacts(Contacts contacts);
	public List findContactsByPeriod(Period pd);
	public List searchContactsByName(String str);
	public List getMaxContactsID();
	//Catalogs Service
	public void saveCatalogs(Catalogs catalogs);
	public List <Catalogs> findAllCatalogs();
	public Catalogs findCatalogsByID(int id);
	public List<Catalogs> searchCatalogs(Catalogs catalogs);
	public List findCatalogsByPeriod(Period pd);
	public List findCatalogsBarByPeriod(Period pd);
	public List searchCatalogsByName(String str);
	public List searchCatalogsByBarcode(String str);
	public List getMaxCatalogsID();
	//Accounts Service
	public void saveAccounts(Accounts accounts);
	public List <Accounts> findAllAccounts();
	public Accounts findAccountsByID(int id);
	public List<Accounts> searchAccounts(Accounts accounts);
	public List findAccountsByPeriod(Period pd);
	public List searchAccountsByName(String str);
	public List getMaxAccountsID();
	//AccountTransactions Service
	public void saveAccountTransactions(AccountTransactions accounttransactions);
	public List <AccountTransactions> findAllAccountTransactions();
	public AccountTransactions findAccountTransactionsByID(int id);
	public List<AccountTransactions> searchAccountTransactions(AccountTransactions accounttransactions);
	public List findAccountTransactionsByPeriod(Period pd);
	public List searchAccountTransactionsByName(String str);
	public List getMaxAccountTransactionsID();
	//Invoices Service
	public void saveInvoices(Invoices invoices);
	public List <Invoices> findAllInvoices();
	public Invoices findInvoicesByID(int id);
	public List<Invoices> searchInvoices(Invoices invoices);
	public List ListInvoicesByStatusAndUser(int usr);
	public List findInvoicesByPeriod(Period pd);
	public List findSalesByPeriod(Period pd);
	public List searchInvoicesByName(String str);
	public List getInvoicesItemsByInvoiceID(int id);
	public List getMaxInvoicesID();
	//InvoicesItems Service
	public void saveInvoicesItems(InvoicesItems invoicesitems);
	public List <InvoicesItems> findAllInvoicesItems();
	public InvoicesItems findInvoicesItemsByID(int id);
	public List<InvoicesItems> searchInvoicesItems(InvoicesItems invoicesitems);
	public List findInvoicesItemsByPeriod(Period pd);
	public List searchInvoicesItemsByName(String str);
	public List getMaxInvoicesItemsID();
	//Purchases Service
	public void savePurchases(Purchases purchases);
	public List <Purchases> findAllPurchases();
	public Purchases findPurchasesByID(int id);
	public List<Purchases> searchPurchases(Purchases purchases);
	public List findPurchasesByPeriod(Period pd);
	public List searchPurchasesByName(String str);
	public List getMaxPurchasesID();
	//PurchasesItems Service
	public void savePurchasesItems(PurchasesItems purchasesitems);
	public List <PurchasesItems> findAllPurchasesItems();
	public PurchasesItems findPurchasesItemsByID(int id);
	public List<PurchasesItems> searchPurchasesItems(PurchasesItems purchasesitems);
	public List findPurchasesItemsByPeriod(Period pd);
	public List findStocksVerificationReport(Period pd);
	public List findFastmovingStocksReport(Period pd);
	public List findLowStocksReport(Period pd);
	public List searchPurchasesItemsByName(String str);
	public List getMaxPurchasesItemsID();
	public List getPurchasesItemsbyPurchaseID(int id);
	//Payments Service
	public void savePayments(Payments payments);
	public List <Payments> findAllPayments();
	public Payments findPaymentsByID(int id);
	public List<Payments> searchPayments(Payments payments);
	public List findPaymentsByPeriod(Period pd);
	public List searchPaymentsByName(String str);
	public List getMaxPaymentsID();
	//Returnstock Service
	public void saveReturnstock(Returnstock returnstock);
	public List <Returnstock> findAllReturnstock();
	public Returnstock findReturnstockByID(int id);
	public List<Returnstock> searchReturnstock(Returnstock returnstock);
	public List ListReturnstockByStatusAndUser(int usr);
	public List findReturnstockByPeriod(Period pd);
	public List findReturnByPeriod(Period pd);
	public List searchReturnstockByName(String str);
	public List getReturnstockItemsByReturnID(int id);
	public List getMaxReturnstockID();
	//ReturnstockItems Service
	public void saveReturnstockItems(ReturnstockItems returnstockitems);
	public List <ReturnstockItems> findAllReturnstockItems();
	public ReturnstockItems findReturnstockItemsByID(int id);
	public List<ReturnstockItems> searchReturnstockItems(ReturnstockItems returnstockitems);
	public List findReturnstockItemsByPeriod(Period pd);
	public List searchReturnstockItemsByName(String str);
	public List getMaxReturnstockItemsID();
	//Shipments Service
	public void saveShipments(Shipments shipments);
	public List <Shipments> findAllShipments();
	public Shipments findShipmentsByID(int id);
	public List<Shipments> searchShipments(Shipments shipments);
	public List findShipmentsByPeriod(Period pd);
	public List searchShipmentsByName(String str);
	public List getMaxShipmentsID();
	//Settings Service
	public void saveSettings(Settings settings);
	public List <Settings> findAllSettings();
	public Settings findSettingsByID(int id);
	public List<Settings> searchSettings(Settings settings);
	public List findSettingsByPeriod(Period pd);
	public List searchSettingsByName(String str);
	public List getMaxSettingsID();


}
