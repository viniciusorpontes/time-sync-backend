package br.com.timesync.services;

import br.com.timesync.dto.*;
import br.com.timesync.entities.Empresa;
import br.com.timesync.entities.EmpresaUsuario;
import br.com.timesync.entities.EmpresaUsuarioId;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.EmpresaRepository;
import br.com.timesync.repositories.EmpresaUsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpresaService {

    private final UsuarioService usuarioService;
    private final EmpresaRepository empresaRepository;
    private final EmpresaUsuarioRepository empresaUsuarioRepository;

    public Empresa buscarEmpresaPorId(Long id) {
        return this.empresaRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public List<BuscarEmpresaDTO> buscarEmpresasPorUsuarioId(Long usuarioId) {
        final List<Empresa> empresas = empresaRepository.buscarEmpresasPorUsuarioId(usuarioId);
        return empresas.stream().map(BuscarEmpresaDTO::toDTO).toList();
    }

    public List<EmpresaUsuarioDTO> buscarUsuariosPorEmpresa(Long empresaId) {
        final List<EmpresaUsuario> empresaUsuarios = empresaUsuarioRepository.findByEmpresaId(empresaId);
        return empresaUsuarios.stream().map(EmpresaUsuarioDTO::toDTO).toList();
    }

    @Transactional
    public Empresa salvar(SalvarEmpresaDTO dto) {

        Empresa empresa = new Empresa();

        empresa.setNome(dto.nome());
        empresa.setAtivo(true);
        empresa = this.empresaRepository.save(empresa);

        final Usuario usuario = usuarioService.buscarPorId(dto.usuarioResponsavelId());

        final EmpresaUsuario empresaUsuario = new EmpresaUsuario(empresa, usuario, true, true, true);

        this.empresaUsuarioRepository.save(empresaUsuario);

        return empresa;
    }

    public Empresa alterar(Long id, AlterarEmpresaDTO dto) {
        final Empresa empresa = buscarEmpresaPorId(id);
        empresa.setNome(dto.nome());
        return this.empresaRepository.save(empresa);
    }

    public void deletar(Long id) {
        final Empresa empresa = buscarEmpresaPorId(id);
        empresa.setAtivo(false);
        this.empresaRepository.save(empresa);
    }

    public EmpresaUsuario buscarEmpresaUsuarioPorEmpresaIdEUsuarioId(Long empresaid, Long usuarioId) {
        return this.empresaUsuarioRepository.findById(new EmpresaUsuarioId(empresaid, usuarioId)).orElseThrow(
                () -> new ObjectNotFoundException(String.format(
                        "Usuário não encontrado. Empresa ID: %s - Usuário ID: %s",
                        empresaid,
                        usuarioId)
                )
        );
    }

    public EmpresaUsuarioDTO convidarProdutivo(ConvidarUsuarioDTO convidarUsuarioDTO) {
        final Empresa empresa = buscarEmpresaPorId(convidarUsuarioDTO.empresaId());
        final Usuario usuario = usuarioService.buscarUsuarioPorEmail(convidarUsuarioDTO.email());

        final EmpresaUsuarioId id = new EmpresaUsuarioId(empresa.getId(), usuario.getId());

        EmpresaUsuario empresaUsuario = new EmpresaUsuario();

        empresaUsuario.setId(id);
        empresaUsuario.setEmpresa(empresa);
        empresaUsuario.setUsuario(usuario);
        empresaUsuario.setConfirmado(false);
        empresaUsuario.setGestor(convidarUsuarioDTO.gestor());
        empresaUsuario.setAtivo(true);

        empresaUsuario = empresaUsuarioRepository.save(empresaUsuario);

        return EmpresaUsuarioDTO.toDTO(empresaUsuario, usuario);
    }

    public EmpresaUsuarioDTO alterarProdutivo(AlterarProdutivoDTO dto) {
        final Usuario usuario = usuarioService.buscarUsuarioPorEmail(dto.email());

        EmpresaUsuario empresaUsuario = buscarEmpresaUsuarioPorEmpresaIdEUsuarioId(dto.empresaId(), usuario.getId());

        empresaUsuario.setGestor(dto.gestor());

        empresaUsuario = empresaUsuarioRepository.save(empresaUsuario);

        return EmpresaUsuarioDTO.toDTO(empresaUsuario, usuario);
    }

    public void removerProdutivo(Long empresaId, String email) {
        final Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

        final EmpresaUsuario empresaUsuario = buscarEmpresaUsuarioPorEmpresaIdEUsuarioId(empresaId, usuario.getId());

        empresaUsuario.setAtivo(false);

        empresaUsuarioRepository.save(empresaUsuario);
    }

}
