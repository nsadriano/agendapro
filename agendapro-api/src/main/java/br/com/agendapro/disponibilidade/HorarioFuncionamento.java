package br.com.agendapro.disponibilidade;

import jakarta.persistence.*;
import br.com.agendapro.shared.TenantEntity;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horario_funcionamento")
public class HorarioFuncionamento extends TenantEntity {
    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "dia_semana")
    private DayOfWeek diaSemana;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @Column(name = "ativo")
    private Boolean ativo;

    public DayOfWeek getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DayOfWeek diaSemana) { this.diaSemana = diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

}