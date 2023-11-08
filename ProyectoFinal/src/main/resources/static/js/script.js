document.addEventListener("DOMContentLoaded", function () {
    const rangoHorarioContainer = document.getElementById("rangoHorarioContainer");
    const agregarRangoHorarioBtn = document.getElementById("agregarRangoHorario");

    agregarRangoHorarioBtn.addEventListener("click", function () {
        console.log("Agregando nuevo RangoHorario");
        const nuevoRangoHorario = createRangoHorario();
        rangoHorarioContainer.appendChild(nuevoRangoHorario);
    });

    function createRangoHorario() {
        const nuevoRangoHorario = document.createElement("div");
        nuevoRangoHorario.classList.add("rango-horario"); // Añadir una clase para el estilo si es necesario
        nuevoRangoHorario.innerHTML = `
          <div class="form-group">
              <label>Día de la semana:</label>
              <select name="diaSemana" class="controls diaSemana">
                  <option value="Lunes">Lunes</option>
                  <option value="Martes">Martes</option>
                  <option value="Miércoles">Miércoles</option>
                  <option value="Jueves">Jueves</option>
                  <option value="Viernes">Viernes</option>
              </select>
          </div>
          <div class="form-group">
              <label>Hora de inicio:</label>
              <select class="controls horaInicio" name="horaInicio"></select>
          </div>
          <div class="form-group">
              <label>Hora de fin:</label>
              <select class="controls horaFin" name="horaFin"></select>
          </div>
        `;

        // Poblamos las opciones de tiempo para los selects de este nuevo rango horario
        populateTimeOptions(nuevoRangoHorario.querySelector(".horaInicio"));
        populateTimeOptions(nuevoRangoHorario.querySelector(".horaFin"));

        return nuevoRangoHorario;
      }

      // Esta función se llamará cada vez que se agregue un nuevo rango horario
      function addRangoHorario() {
        const rangoHorarioContainer = document.getElementById(
          "rangoHorarioContainer"
        );
        rangoHorarioContainer.appendChild(createRangoHorario());
      }

    var startTime = document.getElementById("horaInicio");
        var endTime = document.getElementById("horaFin");

        function populateTimeOptions(input) {
          var options = "";
          for (var i = 0; i < 24; i++) {
            for (var j = 0; j < 2; j++) {
              var hour = i.toString().padStart(2, "0");
              var minute = j === 0 ? "00" : "30";
              options += '<option value="' + hour + ':' + minute + '">' + hour + ':' + minute + '</option>';
            }
          }
          input.innerHTML = options;
        }

        populateTimeOptions(startTime);
        populateTimeOptions(endTime);
});
