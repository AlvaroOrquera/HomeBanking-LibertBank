    const { createApp } = Vue
    const app = createApp({
        data() {
            return {
                data: [],
                fechaYHora: [],
                selectedType: 0,
                selectedColor: 0,
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
            createCard() {
                axios.post("/api/clients/current/cards?colors=" + this.selectedColor + "&type=" + this.selectedType)
                    .then(response => {
                        console.log(response.cardDTOS)
                        if (response.status === 201) {
                            this.successMsg();
                            this.statusTransaction = true;
                        } else {
                            this.errorMsg();
                            this.statusTransaction = false;
                        }
                    })
                    .catch(error => console.log(error));
            },
            
            errorMsg() {
                Swal.fire({
                    background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                    color: "white",
                    icon: "error",
                    title: "Oops...",
                    text: "Something went wrong!",
                });
            },
            
            successMsg() {
                Swal.fire({
                    background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                    color: "white",
                    icon: "success",
                    title: "Success!",
                    text: "Transaction created successfully!",
                }).then(() => {
                    window.location.href = "./card.html";
                });
            }
        }
    }).mount('#app')