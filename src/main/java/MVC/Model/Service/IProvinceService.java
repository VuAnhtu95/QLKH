package MVC.Model.Service;

import MVC.Model.Entities.Province;
import org.springframework.stereotype.Service;

@Service
public interface IProvinceService {
    Iterable<Province> findAll();

    Province findById(Long id);

    void save(Province province);

    void remove(Long id);
}
