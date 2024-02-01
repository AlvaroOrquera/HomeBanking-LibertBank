const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            fechaYHora: [],
            dateTime: -1,
            endTime: -1,
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
                    this.data.reverse();
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
                const opciones = { year: 'numeric', month: 'numeric', day: 'numeric', hour: '2-digit', minute: '2-digit' };
                return fechaFormateada.toLocaleString('en-US', opciones);
            }
            return '';
        },
        logout() {
            axios.post("/api/logout")
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "./login.html"
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        },
        descargarPdf() {
            // Verifica que se hayan seleccionado fechas
            if (!this.dateTime || !this.endTime) {
                // Muestra un SweetAlert indicando que los datos no son válidos
                this.invalidDataMsg();
                return;
            }
            // Construye la URL para la descarga del PDF
            const pdfUrl = `/api/accounts/${this.id}/transactions/pdf?dateTime=${this.dateTime}&endTime=${this.endTime}`;
            // Realiza la solicitud para obtener el PDF
            axios.get(pdfUrl, { responseType: 'blob' })
                .then(response => {
                    // Crea una URL de objeto para el blob del PDF
                    const blob = new Blob([response.data], { type: 'application/pdf' });
                    const url = window.URL.createObjectURL(blob);
                    // Abre una nueva ventana o pestaña para la descarga del PDF
                    window.open(url, '_blank');
                    // Libera la URL de objeto cuando ya no se necesita
                    window.URL.revokeObjectURL(url);
                    // Muestra un SweetAlert indicando que el PDF se ha descargado con éxito
                    this.pdfDownloadedMsg();
                })
                .catch(error => {
                    console.log(error);
                    // Manejo de errores
                });
        },
        pdfDownloadedMsg() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "success",
                title: "Success!",
                text: "PDF downloaded successfully!",
            });
        },
        invalidDataMsg() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "error",
                title: "Invalid Dates!",
                text: "Please enter correct date values.",
            });
        },
        cerrarSession() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
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
                confirmButtonColor: "#8B0000",
                cancelButtonColor: "#717171",
                confirmButtonText: "Yes, I'm sure!",
            }).then((result) => {
                if (result.isConfirmed) {
                    this.logout();
                }
            });
        },
    }
}).mount('#app')