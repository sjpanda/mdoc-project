package dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.IDAOAddress;
import domain.IAddress;
import domain.impl.Address;

public class DAOAddress extends HibernateDaoSupport implements IDAOAddress {
	public IAddress getAddressById(final long id){	
		return (IAddress)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException{
				try{	
					List addresses = session.createCriteria(Address.class).add(Restrictions.idEq(id)).list();

					if((addresses != null) && (addresses.size() != 0)){
						return (IAddress) addresses.get(0);
					}
					return null;
				} catch(Exception e){
					System.out.println(e.getMessage());
					return null;
				}
			}
		});
	}






	/*
	 * code before "spring integration with hibernate"
	 */
	/*private Session myGetSession(){
		Session session = null;
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return session;
	}

	public IAddress getAddressById(long id){
		Session session = myGetSession();

		try{
//			Query query = session.createQuery("select a from Address a where a.id = " + id);
//			List addresses = query.list();

			List addresses = session.createCriteria(Address.class).add(Restrictions.idEq(id)).list();

			if((addresses != null) && (addresses.size() != 0)){
				return (IAddress) addresses.get(0);
			}
			return null;
		} catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		} finally {
			session.close();
		}	
	} */
}
