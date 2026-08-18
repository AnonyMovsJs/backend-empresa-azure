package org.ronald.empresabackend.service;

import org.ronald.empresabackend.entity.Cliente;
import org.ronald.empresabackend.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> listar(int pagina, int tamano) {

        if (pagina<0)
            pagina = 0;
        if (tamano<=0)
            tamano = 10;

        Pageable pg = PageRequest.of(pagina, tamano);
        return clienteRepository.findAll(pg);
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crear(Cliente cliente) {
        clienteRepository.findByEmail(cliente.getEmail()).ifPresent(c -> {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        });
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente cliente) {

        Cliente clienteAcutual = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id: " + id));

        clienteAcutual.setNombre(cliente.getNombre());
        clienteAcutual.setEmail(cliente.getEmail());
        return clienteRepository.save(clienteAcutual);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado con id: " + id);
        }

        clienteRepository.deleteById(id);
    }


}
