const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            fechaYHora: [],
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("/api/clients/1")
                .then(data => {
                    this.data = data.data.cardDTOS
                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        formatFechaYHora(fechaYHora) {
            if (fechaYHora) {
                const fechaFormateada = new Date(fechaYHora);
                const opciones = { year: 'numeric', month: 'numeric' };
                return fechaFormateada.toLocaleString('en-US', opciones);
            }
            return '';
        },
    }
}).mount('#app')