package MVC.Model.Service;

import MVC.Model.Entities.Province;
import MVC.Repository.IProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProvinceService implements IProvinceService{

    @Autowired
    private IProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return (List<Province>) provinceRepository.findAll();
    }

    @Override
    public Province findById(Long id) {
        return provinceRepository.findById(id).get();
    }

    @Override
    public void save(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void remove(Long id) {
        provinceRepository.deleteById(id);
    }
}
