package dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import util.HibernateUtil;
import dao.IDAOEntreprise;
import domain.IEntreprise;

public class DAOEntreprise extends HibernateDaoSupport implements IDAOEntreprise{
	public IEntreprise getEntrepriseByIdContact(long idContact){
		try{
			List entreprises = getHibernateTemplate().find("from Entreprise e where e.id = " + idContact);
			if(entreprises.size() <= 0){
				return null;
			}
			return ((IEntreprise)entreprises.get(0));
		} catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
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
	
	public IEntreprise getEntrepriseByIdContact(long idContact){
		Session session = myGetSession();

		try{
			Query query = session.createQuery("from Entreprise e where e.id = " + idContact);
			List entreprises = query.list();
			if(entreprises.size() <= 0){
				return null;
			}
			return ((IEntreprise)entreprises.get(0));
		} catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		} finally {
			session.close();
		}
	} */
}
