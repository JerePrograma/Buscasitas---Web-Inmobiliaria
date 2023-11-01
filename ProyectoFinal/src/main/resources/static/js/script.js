<script>
    document.addEventListener("DOMContentLoaded", function() {
        const rangoHorarioContainer = document.getElementById("rangoHorarioContainer");
        const agregarRangoHorarioBtn = document.getElementById("agregarRangoHorario");

        agregarRangoHorarioBtn.addEventListener("click", function() {
            const nuevoRangoHorario = document.createElement("div");
            nuevoRangoHorario.innerHTML = `
                <div class="form-group">
                    <label for="diaSemana">Día de la semana:</label>
                    <select name="diaSemana" class="diaSemana">
                        <option value="Lunes">Lunes</option>
                                <option value="Martes">Martes</option>
                                <option value="Miercoles">Miercoles</option>
                                <option value="Jueves">Jueves</option>
                                <option value="Viernes">Viernes</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="horaInicio">Hora de inicio:</label>
                    <input type="time" name="horaInicio" class="horaInicio">
                </div>
                <div class="form-group">
                    <label for="horaFin">Hora de fin:</label>
                    <input type="time" name="horaFin" class="horaFin">
                </div>
            `;
            rangoHorarioContainer.appendChild(nuevoRangoHorario);
        });
    });
</script>