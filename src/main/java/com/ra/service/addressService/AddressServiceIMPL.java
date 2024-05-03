package com.ra.service.addressService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ra.model.entity.Address;
import com.ra.repository.IAddressRepository;

@Service
public class AddressServiceIMPL implements IAddressService {

    @Autowired private IAddressRepository addressRepository;
    
    @Override
    public Page<Address> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
