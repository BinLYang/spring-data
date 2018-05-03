package com.kinghorse.unittest;


import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.sql.Update;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.kinghorse.springdata.Person;
import com.kinghorse.springdata.repository.PersonRepository;
import com.kinghorse.springdata.service.PersonService;

public class test1 {

	private ApplicationContext ctx = null;
	private PersonService personService = null;
	private PersonRepository personRepository = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		personService =  ctx.getBean(PersonService.class);
		personRepository = ctx.getBean(PersonRepository.class);
	}
	
	/**
	 * JpaSpecificationExecutor�е� findAll(Specification<Person> arg0, Pageable arg1)����
	 * Specification��װ��JPA criteria��ѯ����
	 * Pageable:��װ�˷�ҳ������Ϣ������pageNo��pageSize��sort
	 */
	@Test
	public void testJpaSpecificationExecutor(){
		int pageNo = 3;
		int pageSize = 5;
		PageRequest pageable = new PageRequest(pageNo, pageSize);
		
		Specification<Person> specification = new Specification<Person>() {

			/**
			 * root:�����ѯ��ʵ����
			 * Query:���Դ��л�ȡ��Root���󣬼���֪JPA Criteria��ѯҪ��ѯ��һ��ʵ���࣬����������Ӳ�ѯ�����������Խ��EntityManager
			 * ����õ����ղ�ѯ��TypedQuery����
			 * cb:CriteriaBuilder�������ڴ���Criteria��ض���Ĺ�������Ȼ���Դ��л�ȡ��Predicate����
			 * return:Predicate���ͣ�����һ����ѯ����
			 */
			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> Query, CriteriaBuilder cb) {
				
				Path path = root.get("id");
				Predicate predicate = cb.gt(path, 5);
				return predicate;
			}
		};
		Page<Person> page = personRepository.findAll(specification, pageable);
		System.out.println(page.getContent());
	}
	
	
	@Test
	public void testPaingAndSorting() {
		//	pageNoͨ���Ǵ�0��ʼ��
		int pageNo = 3;
		int pageSize = 5;
		//	Pageable �ӿ�ͨ��ʹ������ʵ����PageRequest,���з�װ����Ҫ��ҳ����Ϣ
		//PageRequest pageRequest = new PageRequest(pageNo, pageSize);
		
		//Sort��װ��������Ϣ
		Order order1 = new Order(Direction.DESC, "id");
		Order order2 = new Order(Direction.ASC, "email");
		Sort sort = new Sort(order1, order2);
		PageRequest pageRequest = new PageRequest(pageNo, pageSize, sort);
		
		
		Page<Person> page = personRepository.findAll(pageRequest);
		
		System.out.println(page.getContent());
	}
	
	@Test
	public void testSave(){
		List<Person> persons = new ArrayList<>();
		
		for (int i = 'a'; i <= 'z'; i++) {
			Person person = new Person();
			person.setLastName((char)i + "" + (char)i);
			person.setEmail((char)i + "" + (char)i + "@hirisun.com");
			person.setBirth(new Date());
			
			persons.add(person);
		}
		
		personService.savePerson(persons);
	}
	
	@Test
	public void testUpdate(){
		personService.updatePersonEmail("hello@hirisun.com", 1);
	}
	
	@Test
	public void testPerson(){
		
		Person person = personRepository.getByLastName("LiShuaiKe");
		
		System.out.println(person);
	}
	
	@Test
	public void testJPA(){
		
	}
	
	@Test
	public void test() {
		DataSource dataSource = (DataSource) ctx.getBean("dataSource");
		
		System.out.println(dataSource);
	}

}
