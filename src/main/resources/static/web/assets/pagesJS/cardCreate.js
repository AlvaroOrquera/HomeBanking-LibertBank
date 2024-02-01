const { createApp } = Vue
const app = createApp({
    data() {
        return {
            data: [],
            fechaYHora: [],
            selectedType: -1,
            selectedColor: -1,
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
                icon: "error",
                title: "Oops...",
                text: "Something went wrong!",
            });
        },
        
        successMsg() {
            Swal.fire({
                background: "linear-gradient(to right, #2B0000, #440000) no-repeat 0 0 / cover",
                icon: "success",
                title: "Success!",
                text: "Card created successfully!",
            }).then(() => {
                window.location.href = "/web/cards.html";
            });
        }
    }
}).mount('#app')