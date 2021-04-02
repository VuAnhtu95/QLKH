package MVC.Repository;

import MVC.Model.Entities.Customer;
import MVC.Model.Entities.Province;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends PagingAndSortingRepository<Customer,Long> {
    Iterable<Customer> findAllByProvince(Province province);
}
