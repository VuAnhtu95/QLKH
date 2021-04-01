package MVC.Model.Service;

import MVC.Model.Entities.Province;
import MVC.Repository.IProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProvinceService implements IProvinceService{

    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public Iterable<Province> findAll() {
        return null;
    }

    @Override
    public Province findById(Long id) {
        return null;
    }

    @Override
    public void save(Province province) {

    }

    @Override
    public void remove(Long id) {

    }
}
