package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import util.ApplicationContextUtils;
import dao.IDAOContact;
import dao.IDAOContactGroup;
import dao.IDAOPhoneNumber;
import domain.IAddress;
import domain.IContact;
import domain.IContactGroup;
import domain.IEntreprise;
import domain.IPhoneNumber;
import domain.impl.Contact;
import domain.impl.Entreprise;

public class DAOContact extends HibernateDaoSupport implements IDAOContact {
	public boolean createContact(String fname, String lname, String email, IAddress address, Set<IPhoneNumber> profiles, int numSiret){		
		try {
			IContact c;
			if(numSiret <= 0){
				c = (Contact)ApplicationContextUtils.getApplicationContext().getBean("ContactConstrWithArgs");
			} else {
				c = (Entreprise)ApplicationContextUtils.getApplicationContext().getBean("Entreprise");
				((IEntreprise)c).setNumSiret(numSiret);
			}

			if(address != null){
				getHibernateTemplate().save(address);
			}

			c.setFirstname(fname);
			c.setLastname(lname);
			c.setEmail(email);
			c.setAddress(address);	
			getHibernateTemplate().save(c);

			if(profiles != null){
				for(IPhoneNumber profile : profiles){
					profile.setContact(c);
					c.getProfiles().add(profile);
					getHibernateTemplate().save(profile);
				}
			}

			return true;	
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateContact(IContact c, String fname, String lname, String email, 
			String street, String zip, String city, String country, String home, String office, String mobile, int siretnum){
		try{
			System.out.println("version prev : " + c.getVersion());

			c.setFirstname(fname);
			c.setLastname(lname);
			c.setEmail(email);
			c.getAddress().setStreet(street);
			c.getAddress().setZip(zip);
			c.getAddress().setCity(city);
			c.getAddress().setCountry(country);
			checkAndAdd("home", home, c, c.getProfiles());
			checkAndAdd("office", office, c, c.getProfiles());
			checkAndAdd("mobile", mobile, c, c.getProfiles());

			if(siretnum == -1){
				if(c instanceof IEntreprise){
					return false;
				}
			} else {
				if(c instanceof IEntreprise){
					((IEntreprise)c).setNumSiret(siretnum);
				} else {
					return false;
				}
			}

			getHibernateTemplate().update(c);

			System.out.println("version : " + c.getVersion());	

			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteContact(String id){
		long idNum = 0;
		try{
			idNum = Integer.parseInt(id);
		} catch (NumberFormatException e){
			e.printStackTrace();
			return false;
		}

		try{
			IContact c = (IContact)getHibernateTemplate().get(Contact.class, idNum);
			c.getProfiles().clear();

			ApplicationContext context = ApplicationContextUtils.getApplicationContext();
			IDAOContactGroup daoContactGroup = (IDAOContactGroup)context.getBean("DAOContactGroup");
			for(IContactGroup cg : c.getBooks()){
				cg.getContacts().remove(c);
				if(cg.getContacts().size() == 0){
					daoContactGroup.deleteContactGroup(cg);
				}
			}

			getHibernateTemplate().delete(c);

			return true;	
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public List searchContact(final String fname, final String lname, final String email, final IAddress address,
			final String home, final String office, final String mobile){
		return (List)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				try{
					Criteria criteria = session.createCriteria(Contact.class);
					if(! fname.isEmpty()){
						criteria.add(Restrictions.like("firstname", fname, MatchMode.ANYWHERE));
					}
					if(! lname.isEmpty()){
						criteria.add(Restrictions.like("lastname", lname, MatchMode.ANYWHERE));
					}
					if(! email.isEmpty()){
						criteria.add(Restrictions.like("email", email, MatchMode.ANYWHERE));
					}
					if(! address.getStreet().isEmpty()){
						criteria.add(Restrictions.like("address.street", address.getStreet(), MatchMode.ANYWHERE));
					}
					if(! address.getZip().isEmpty()){
						criteria.add(Restrictions.like("address.zip", address.getZip(), MatchMode.ANYWHERE));
					}
					if(! address.getCity().isEmpty()){
						criteria.add(Restrictions.like("address.city", address.getCity(), MatchMode.ANYWHERE));
					}
					if(! address.getCountry().isEmpty()){
						criteria.add(Restrictions.like("address.country", address.getCountry(), MatchMode.ANYWHERE));
					}

					List contacts = criteria.list();

					if(home.isEmpty() && office.isEmpty() && mobile.isEmpty()){
						return contacts;
					}

					List toRemove = new ArrayList();

					ApplicationContext context = ApplicationContextUtils.getApplicationContext();
					IDAOPhoneNumber daoP = (IDAOPhoneNumber)context.getBean("DAOPhoneNumber");


					for(int i=0; i<contacts.size(); i++){
						IContact c = (IContact)contacts.get(i);

						List pns = daoP.getPhoneNumbersByIdContact(c.getId());
						if((! keep("home", home, pns)) || (! keep("office", office, pns)) || (! keep("mobile", mobile, pns))){
							toRemove.add(c);
						}
					}

					contacts.removeAll(toRemove);
					return contacts;
				} catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
		});
	}

	public Object[] getContactById(String id){
		try{
			List contacts = getHibernateTemplate().find("select c, a from Contact c, Address a where c.id = " + id + " and c.address= a");
			if((contacts != null) && (contacts.size() != 0)){
				return (Object[]) contacts.get(0);
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public List getAllContacts(){
		return (List)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				try{
					Query query = session.createQuery("from Contact c left join fetch c.address address");
					List contacts = query.setCacheable(true).list();
					return contacts;
				} catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
		});
	}

	public List getContactGroupByIdContact(String idContact){
		try{
			long idNum = Integer.parseInt(idContact);
			IContact c = (IContact)getHibernateTemplate().get(Contact.class, idNum);

			List contactGroup = getHibernateTemplate().find("select elements(c.books) from Contact c where c.id = " + idContact);

			return contactGroup;
		} catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("================================ idContact = "+idContact+"======================================================");
			return null;
		}
	}

	public boolean generateContacts(){
		try{
			List<IContact> contacts = new ArrayList<IContact>();
			List<IAddress> addresses = new ArrayList<IAddress>();
			List<IPhoneNumber> phoneNumbers = new ArrayList<IPhoneNumber>();

			contacts.add((IContact)ApplicationContextUtils.getApplicationContext().getBean("ContactExp1"));
			contacts.add((IContact)ApplicationContextUtils.getApplicationContext().getBean("EntrepriseExp1"));
			contacts.add((IContact)ApplicationContextUtils.getApplicationContext().getBean("EntrepriseExp2"));
			
			((IEntreprise)contacts.get(1)).setNumSiret(999999999);
			((IEntreprise)contacts.get(2)).setNumSiret(888888888);

			for(int i=1; i<=3; i++){
				addresses.add((IAddress)ApplicationContextUtils.getApplicationContext().getBean("AddressExp" + i));
				contacts.get(i-1).setAddress(addresses.get(i-1));
				getHibernateTemplate().save(contacts.get(i-1));
			}

			for(int i=1; i<=9; i++){
				phoneNumbers.add((IPhoneNumber)ApplicationContextUtils.getApplicationContext().getBean("PhoneNumberExp" + i));
			}

			for(int i=0; i<=2; i++){
				for(int j=0; j<3; j++){
					phoneNumbers.get(j+3*i).setContact(contacts.get(i));
					contacts.get(i).getProfiles().add(phoneNumbers.get(j+3*i));
					getHibernateTemplate().save(phoneNumbers.get(j+3*i));
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void checkAndAdd(String kind, String number, IContact contact, Set<IPhoneNumber> profiles){
		if(number.equals("")){
			for(IPhoneNumber p : profiles){
				if(p.getPhoneKind().equalsIgnoreCase(kind)){
					getHibernateTemplate().delete(p);
					break;
				}
			}
		} else {
			boolean add = true;
			for(IPhoneNumber p : profiles){
				if(p.getPhoneKind().equalsIgnoreCase(kind)){
					add = false;
					p.setPhoneNumber(number);
				}
			}
			if(add){
				IPhoneNumber p = (IPhoneNumber)ApplicationContextUtils.getApplicationContext().getBean("PhoneNumber");
				p.setPhoneKind(kind);
				p.setPhoneNumber(number);
				p.setContact(contact);
				getHibernateTemplate().save(p);
				profiles.add(p);
			}
		}
	}

	private boolean keep(String kind, String number, List phoneNumbers){
		if(number.isEmpty()){
			return true;
		}
		if((! number.isEmpty()) && (phoneNumbers == null)){
			return false;
		}
		for(Object o : phoneNumbers){
			IPhoneNumber p = (IPhoneNumber) o;
			if(p.getPhoneKind().equalsIgnoreCase(kind) && 
					(p.getPhoneNumber().equalsIgnoreCase(number) || (p.getPhoneNumber().contains(number)))){
				return true;
			}
		}
		return false;
	}












	/*  
	 * code before spring integration with hibernate 
	 */
	/*	private Session myGetSession(){
		Session session = null;
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		} catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}

	public boolean createContact(String fname, String lname, String email, IAddress address, Set<IPhoneNumber> profiles, int numSiret){		
		Session session = myGetSession();
		Transaction tx = null;

		try{
			tx = session.beginTransaction();
			IContact c;
			if(numSiret <= 0){
				c = new Contact();
				//c = (Contact)ApplicationContextUtils.getApplicationContext().getBean("ContactSetter");
			} else {
				c = new Entreprise();
				//c = (Entreprise)ApplicationContextUtils.getApplicationContext().getBean("Entreprise");
				((IEntreprise)c).setNumSiret(numSiret);
			}

			c.setFirstname(fname);
			c.setLastname(lname);
			c.setEmail(email);
			c.setAddress(address);			
			session.save(c);

			if(profiles != null){
				for(IPhoneNumber profile : profiles){
					profile.setContact(c);
					c.getProfiles().add(profile);
					//session.save(profile);
				}
			}

			tx.commit();
			return true;
		} catch(Exception e){
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return false;
		} finally {
			session.close();
		}	
	}

	public boolean updateContact(IContact c, String fname, String lname, String email, 
			String street, String zip, String city, String country, String home, String office, String mobile, int siretnum){
//		long idNum = 0;
//		try{
//			idNum = Integer.parseInt(id);
//		} catch (NumberFormatException e){
//			e.printStackTrace();
//			return false;
//		}

		Session session = myGetSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
//			IContact c = (IContact)session.get(Contact.class, idNum);
//			if(c == null){
//				System.out.println("Contact " + id + " not found");
//				return false;
//			}

			System.out.println("version prev : " + c.getVersion());

			c.setFirstname(fname);
			c.setLastname(lname);
			c.setEmail(email);
			c.getAddress().setStreet(street);
			c.getAddress().setZip(zip);
			c.getAddress().setCity(city);
			c.getAddress().setCountry(country);
			checkAndAdd("home", home, c, c.getProfiles(), session);
			checkAndAdd("office", office, c, c.getProfiles(), session);
			checkAndAdd("mobile", mobile, c, c.getProfiles(), session);

			if(siretnum == -1){
				if(c instanceof IEntreprise){
					return false;
				}
			} else {
				if(c instanceof IEntreprise){
					((IEntreprise)c).setNumSiret(siretnum);
				} else {
					return false;
				}
			}

			session.update(c);

			tx.commit();

			return true;
		} catch(Exception e){
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return false;
		} finally {
			System.out.println("version : " + c.getVersion());
			session.close();
		}	
	}

	public boolean deleteContact(String id){
		long idNum = 0;
		try{
			idNum = Integer.parseInt(id);
		} catch (NumberFormatException e){
			e.printStackTrace();
			return false;
		}

		Session session = myGetSession();
		Transaction tx = null;

		try{
			tx = session.beginTransaction();
			IContact c = (IContact)session.get(Contact.class, idNum);
			IAddress a = c.getAddress();

			c.getProfiles().clear();
			c.getBooks().clear();

			session.delete(a);
			session.delete(c);

			tx.commit();

			return true;
		} catch(Exception e){
			e.printStackTrace();
			if(tx != null)
				tx.rollback();
			return false;
		} finally {
			session.close();
		}	
	}

	public List searchContact(String fname, String lname, String email, IAddress address,
			String home, String office, String mobile){
		Session session = myGetSession();

		try{
			//			StringBuffer s = new StringBuffer();
			//			
			//			s.append("select c, a from Contact c, Address a where c.address = a ");
			//			
			//			if(! fname.isEmpty()){
			//				s.append("and c.firstname like '%" + fname + "%' ");
			//			}
			//			if(! lname.isEmpty()){
			//				s.append("and c.lastname like '%" + lname + "%' ");
			//			}
			//			if(! email.isEmpty()){
			//				s.append("and c.email like '%" + email + "%' ");
			//			}
			//			if(! address.getStreet().isEmpty()){
			//				s.append("and a.street like '%" + address.getStreet() + "%' ");
			//			}
			//			if(! address.getZip().isEmpty()){
			//				s.append("and a.zip like '%" + address.getZip() + "%' ");
			//			}
			//			if(! address.getCity().isEmpty()){
			//				s.append("and a.city like '%" + address.getCity() + "%' ");
			//			}
			//			if(! address.getCountry().isEmpty()){
			//				s.append("and a.country like '%" + address.getCountry() + "%' ");
			//			}
			//			System.out.println("Query : " + s.toString());
			//			Query query = session.createQuery(s.toString());
			//			List contacts = query.list();


			Criteria criteria = session.createCriteria(Contact.class);
			if(! fname.isEmpty()){
				criteria.add(Restrictions.like("firstname", fname, MatchMode.ANYWHERE));
			}
			if(! lname.isEmpty()){
				criteria.add(Restrictions.like("lastname", lname, MatchMode.ANYWHERE));
			}
			if(! email.isEmpty()){
				criteria.add(Restrictions.like("email", email, MatchMode.ANYWHERE));
			}
			if(! address.getStreet().isEmpty()){
				criteria.add(Restrictions.like("address.street", address.getStreet(), MatchMode.ANYWHERE));
			}
			if(! address.getZip().isEmpty()){
				criteria.add(Restrictions.like("address.zip", address.getZip(), MatchMode.ANYWHERE));
			}
			if(! address.getCity().isEmpty()){
				criteria.add(Restrictions.like("address.city", address.getCity(), MatchMode.ANYWHERE));
			}
			if(! address.getCountry().isEmpty()){
				criteria.add(Restrictions.like("address.country", address.getCountry(), MatchMode.ANYWHERE));
			}

			List contacts = criteria.list();

			if(home.isEmpty() && office.isEmpty() && mobile.isEmpty()){
				return contacts;
			}

			List toRemove = new ArrayList();

			//DAOPhoneNumber daoP = new DAOPhoneNumber();
			ApplicationContext context = ApplicationContextUtils.getApplicationContext();
			DAOPhoneNumber daoP = (DAOPhoneNumber)context.getBean("DAOPhoneNumber");


			for(int i=0; i<contacts.size(); i++){
				//Object[] o = (Object[])contacts.get(i);
				//Contact c = (Contact) o[0];

				IContact c = (IContact)contacts.get(i);

				List pns = daoP.getPhoneNumbersByIdContact(c.getId());
				if((! keep("home", home, pns)) || (! keep("office", office, pns)) || (! keep("mobile", mobile, pns))){
					toRemove.add(c);
				}
			}

			contacts.removeAll(toRemove);
			return contacts;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public Object[] getContactById(String id){
		Session session = myGetSession();

		try{
			Query query = session.createQuery("select c, a from Contact c, Address a where c.id = " + id + " and c.address= a");
			List contacts = query.list();
			if((contacts != null) && (contacts.size() != 0)){
				return (Object[]) contacts.get(0);
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}	
	}

	public List getAllContacts(){
		Session session = myGetSession();

		try{
			//			Query query = session.createQuery(
			//					"select c, a from Contact c, Address a " +
			//					"where c.address = a");
			Query query = session.createQuery("from Contact c left join fetch c.address address");
			List contacts = query.setCacheable(true).list();
			return contacts;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}	
	}

	private void checkAndAdd(String kind, String number, IContact contact, Set<IPhoneNumber> profiles, Session session){
		if(number.equals("")){
			for(IPhoneNumber p : profiles){
				if(p.getPhoneKind().equalsIgnoreCase(kind)){
					session.delete(p);
					break;
				}
			}
		} else {
			boolean add = true;
			for(IPhoneNumber p : profiles){
				if(p.getPhoneKind().equalsIgnoreCase(kind)){
					add = false;
					p.setPhoneNumber(number);
				}
			}
			if(add){
				//PhoneNumber p = new PhoneNumber(kind, number, contact);
				IPhoneNumber p = (IPhoneNumber)ApplicationContextUtils.getApplicationContext().getBean("PhoneNumber");
				p.setPhoneKind(kind);
				p.setPhoneNumber(number);
				p.setContact(contact);
				session.save(p);
				profiles.add(p);
			}
		}
	}

	private boolean keep(String kind, String number, List phoneNumbers){
		if(number.isEmpty()){
			return true;
		}
		if((! number.isEmpty()) && (phoneNumbers == null)){
			return false;
		}
		for(Object o : phoneNumbers){
			IPhoneNumber p = (IPhoneNumber) o;
			if(p.getPhoneKind().equalsIgnoreCase(kind) && 
					(p.getPhoneNumber().equalsIgnoreCase(number) || (p.getPhoneNumber().contains(number)))){
				return true;
			}
		}
		return false;
	}

	public List getContactGroupByIdContact(String idContact){
		Session session = myGetSession();

		try{
			long idNum = Integer.parseInt(idContact);
			IContact c = (IContact)session.get(Contact.class, idNum);

			Query query = session.createQuery(
					"select elements(c.books) from Contact c where c.id = " + idContact);
			List contactGroup = query.list();

			return contactGroup;
		} catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("================================ idContact = "+idContact+"======================================================");
			return null;
		} finally {
			session.close();
		}	

	}
	 */	
}
