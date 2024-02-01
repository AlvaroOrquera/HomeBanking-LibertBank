const { createApp } = Vue

const app = createApp({
    data() {
        return {
            data: [],
            email: "",
            password: "",
            firstName: "",
            lastName: "",
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
                    return axios.get("/api/clients/current")
                })
                .then(response => {
                    console.log(response)
                    if (response.data.roleType === "ADMIN") {
                        window.location.href = "./ADMIN/manager.html"
                    } else if (response.data.roleType === "CLIENT") {
                        window.location.href = "./accounts.html"
                    }
                })
                .catch(response => {
                    console.log(response)
                })
        },
        login() {
            axios.post("/api/login?email=" + this.email + "&password=" + this.password)
                .then(response => {
                    this.successMsg("Login successful!");
                    return axios.get("/api/clients/current");
                })
                .then(response => {
                    console.log(response);
                    if (response.data.roleType === "ADMIN") {
                        window.location.href = "./ADMIN/manager.html";
                    } else if (response.data.roleType === "CLIENT") {
                        window.location.href = "./accounts.html";
                    }
                })
                .catch(error => {
                    console.log(error);
                    this.errorMsg("Login failed. Please check your credentials and try again.");
                });
        },
        register() {
            const body = {
                "firstName": this.firstName,
                "lastName": this.lastName,
                "email": this.email,
                "password": this.password
            }
            axios.post("/api/clients", body)
                .then(response => {
                    console.log("registered" + this.email);
                    console.log(response.data);
                    this.login();
                })
                .catch(error => console.log(error))
        },
        register() {
            const body = {
                "firstName": this.firstName,
                "lastName": this.lastName,
                "email": this.email,
                "password": this.password
            };

            axios.post("/api/clients", body)
                .then(response => {
                    console.log("registered" + this.email);
                    console.log(response.data);
                    this.login();
                    this.successMsg("Registration successful!");
                })
                .catch(error => {
                    console.log(error);
                    this.errorMsg("Registration failed. Please check your details and try again.");
                });
        },
        successMsg(message) {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "success",
                title: "Success!",
                text: message,
                showConfirmButton: false,  // Oculta el botón "OK"
                timer: 9000, 
            });
        },

        errorMsg(message) {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "error",
                title: "Oops...",
                text: message,
                showConfirmButton: false,  // Oculta el botón "OK"
                timer: 3000, 
            });
        },
        clearData() {
            this.name = ""
            this.lastName = ""
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
            this.firstName = event.target.value
        },
        getLastName(event) {
            this.lastName = event.target.value
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