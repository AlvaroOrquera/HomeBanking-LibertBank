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
            axios.get("/api/clients/current")
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
        logout(){
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "./login.html"
                    }
                })
        },
    }
}).mount('#app')