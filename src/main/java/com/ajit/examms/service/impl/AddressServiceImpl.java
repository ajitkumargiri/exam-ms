package com.ajit.examms.service.impl;

import com.ajit.examms.domain.Address;
import com.ajit.examms.repository.AddressRepository;
import com.ajit.examms.service.AddressService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> partialUpdate(Address address) {
        log.debug("Request to partially update Address : {}", address);

        return addressRepository
            .findById(address.getId())
            .map(
                existingAddress -> {
                    if (address.getAddressLine1() != null) {
                        existingAddress.setAddressLine1(address.getAddressLine1());
                    }
                    if (address.getAddressLine2() != null) {
                        existingAddress.setAddressLine2(address.getAddressLine2());
                    }
                    if (address.getPo() != null) {
                        existingAddress.setPo(address.getPo());
                    }
                    if (address.getPs() != null) {
                        existingAddress.setPs(address.getPs());
                    }
                    if (address.getCity() != null) {
                        existingAddress.setCity(address.getCity());
                    }
                    if (address.getState() != null) {
                        existingAddress.setState(address.getState());
                    }
                    if (address.getPin() != null) {
                        existingAddress.setPin(address.getPin());
                    }

                    return existingAddress;
                }
            )
            .map(addressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
