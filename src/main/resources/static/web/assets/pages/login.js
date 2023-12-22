const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            email: "",
            password: "",
            name: "",
            lastname: "",
            modalVisible: false,
        }
    },
    created() {
        this.clearData()
    },
    methods: {
        login() {
            axios.post("/api/login?email=" + this.email + "&password=" + this.password)
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "./accounts.html"
                    }
                })

                .catch(response => {
                    console.log(response)
                })
        },
        
        register() {
            axios.post("/api/login?firstName=" + this.name + "&lastName=" + this.lastname + "&email=" + this.email + "&password=" + this.password)
                .then(response => {
                    console.log(response)
                    if (response.status == 200) {
                        window.location.href = "./accounts.html"
                    }
                    this.clearData()
                })
                .catch(error => {
                    console.error("Error en la solicitud:", error);
                });
                
        },
        clearData() {
            this.name = ""
            this.lastname = ""
            this.email = ""
            this.password = ""
        },
        getEmail(event) {
            this.email = event.target.value
        },
        getPassword(event) {
            this.password = event.target.value
        },
        getName(event) {
            this.name = event.target.value
        },
        getLastName(event) {
            this.lastname = event.target.value
        },
        togglePassword() {
            const x = document.getElementById("password");
            const eye = document.getElementById("togglePassword");
            eye.classList.toggle("fa-eye-slash");
            const currentInputType = x.getAttribute("type");
            if (currentInputType === "password") {
                x.setAttribute("type", "text");
            } else if (currentInputType === "text") {
                x.setAttribute("type", "password");
            }
        },
        showModal() {
            this.modalVisible = true;
        },

        closeModal() {
            this.modalVisible = false;
        },
    }
}).mount('#app')