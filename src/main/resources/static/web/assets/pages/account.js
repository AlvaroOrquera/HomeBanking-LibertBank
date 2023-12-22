const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            fechaYHora: [],
        }
    },
    created() {
        const search = location.search
        const params = new URLSearchParams(search)
        this.id = params.get('id')
        this.loadData()
        this.formatBudget()

    },
    methods: {
        loadData() {
            axios.get("/api/accounts/" + this.id + "/transactions")
                .then(response => {
                    this.data = response.data
                    console.log(this.data)
                })
                .catch(error => {
                    console.log(error)
                })
        },
        formatBudget(balance) {
            if (balance !== undefined && balance !== null) {
                return balance.toLocaleString("en-US", {
                    style: "currency",
                    currency: "ARS",
                    minimumFractingDigits: 0,
                })
            }
        },
        formatFechaYHora(fechaYHora) {
            if (fechaYHora) {
                const fechaFormateada = new Date(fechaYHora);
                const opciones = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };
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