package eventify.api_spring.domain.buffet;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eventify.api_spring.domain.agenda.Agenda;
import eventify.api_spring.domain.endereco.Endereco;
import eventify.api_spring.domain.usuario.Usuario;
import eventify.api_spring.dto.imagem.ImagemDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Buffet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "Buffet da Alegria")
    @NotBlank
    private String nome;

    @Schema(example = "Venha fazer aqui a sua festa mágica que fará momentos da sua vida se tornarem inesquecíveis no melhor Buffet de São Paulo!")
    @NotBlank
    private String descricao;

    @Schema(example = "500")
    @Min(1)
    private Integer tamanho;

    @Schema(example = "2134.00")
    @DecimalMin("0.0")
    private Double precoMedioDiaria;

    @Schema(example = "100")
    @Min(1)
    private Integer qtdPessoas;

    @Schema(example = "assets/files/comprovantes/buffet1_comp1.pdf")
    @NotBlank
    private String caminhoComprovante;

    @Schema(example = "true")
    private boolean residenciaComprovada;

    @Schema(example = "true")
    private boolean isVisivel;

    @Schema(example = "2023-05-20")

    private LocalDate dataCriacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "buffet_faixa_etaria",
            joinColumns = @JoinColumn(name = "id_buffet"),
            inverseJoinColumns = @JoinColumn(name = "id_faixa_etaria"))
    private Set<FaixaEtaria> faixaEtarias = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "buffet_tipo_evento",
            joinColumns = @JoinColumn(name = "id_buffet"),
            inverseJoinColumns = @JoinColumn(name = "id_tipo_evento"))
    private Set<TipoEvento> tiposEventos = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "buffet_servico",
            joinColumns = @JoinColumn(name = "id_buffet"),
            inverseJoinColumns = @JoinColumn(name = "id_servico"))
    private Set<Servico> servicos = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "buffet")
    private List<Imagem> imagens = new ArrayList<>();

    @OneToMany(mappedBy = "buffet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Agenda> agendas = new ArrayList<>();

    public Buffet(Integer id, String nome, String descricao, Integer tamanho, Double precoMedioDiaria, Integer qtdPessoas, String caminhoComprovante, boolean residenciaComprovada, boolean isVisivel, LocalDate dataCriacao, Endereco endereco, Set<FaixaEtaria> faixaEtarias, Set<TipoEvento> tiposEventos, Set<Servico> servicos, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.precoMedioDiaria = precoMedioDiaria;
        this.qtdPessoas = qtdPessoas;
        this.caminhoComprovante = caminhoComprovante;
        this.residenciaComprovada = residenciaComprovada;
        this.isVisivel = isVisivel;
        this.dataCriacao = dataCriacao;
        this.endereco = endereco;
        this.faixaEtarias = faixaEtarias;
        this.tiposEventos = tiposEventos;
        this.servicos = servicos;
        this.usuario = usuario;
    }

    public List<String> getDescricaoTiposEventos(){
        return tiposEventos.stream().map(TipoEvento::getDescricao).toList();
    }

    public List<ImagemDto> getImagemDto() {
        return imagens.stream()
                .map(i -> new ImagemDto(i.getId(), i.getCaminho(), i.getNome(), i.getTipo(), i.isAtivo(), i.getDataUpload()))
                .toList();
    }
}