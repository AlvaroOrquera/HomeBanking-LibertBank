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
        cerrarSession() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover", // Deep dark red gradient
                customClass: {
                    container: 'custom-swal-container',
                    title: 'custom-swal-title',
                    text: 'custom-swal-text',
                    cancelButton: 'custom-swal-cancel',
                    confirmButton: 'custom-swal-confirm',
                },
                title: "Are you sure?",
                text: "You are about to log out.",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#8B0000", // Dark red
                cancelButtonColor: "#717171", // Light gray
                confirmButtonText: "Yes, I'm sure!",
            }).then((result) => {
                if (result.isConfirmed) {
                    this.logout();
                }
            });
        }
    }
}).mount('#app')