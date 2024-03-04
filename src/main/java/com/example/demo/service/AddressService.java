package com.example.demo.service;

import com.example.demo.domain.Address;
import com.example.demo.model.AddressDTO;
import com.example.demo.repos.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.example.demo.util.NotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<AddressDTO> findAll() {
        final List<Address> addresss = addressRepository.findAll(Sort.by("id"));
        return addresss.stream()
                .map((address) -> mapToDTO(address, new AddressDTO()))
                .collect(Collectors.toList());
    }

    public AddressDTO get(final Long id) {
        return addressRepository.findById(id)
                .map(address -> mapToDTO(address, new AddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AddressDTO addressDTO) {
        final Address address = new Address();
        mapToEntity(addressDTO, address);
        return addressRepository.save(address).getId();
    }

    public void update(final Long id, final @Valid AddressDTO addressDTO) {
        final Address address = addressRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(addressDTO, address);
        addressRepository.save(address);
    }

    public void delete(final Long id) {
        addressRepository.deleteById(id);
    }

    private AddressDTO mapToDTO(final Address address, final AddressDTO addressDTO) {
        addressDTO.setId(address.getId());
        addressDTO.setLine1(address.getLine1());
        addressDTO.setLine2(address.getLine2());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPincode(address.getPincode());
        addressDTO.setState(address.getState());
        return addressDTO;
    }

    private Address mapToEntity(final AddressDTO addressDTO, final Address address) {
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setCity(addressDTO.getCity());
        address.setCountry(addressDTO.getCountry());
        address.setPincode(addressDTO.getPincode());
        address.setState(addressDTO.getState());
        return address;
    }

}
