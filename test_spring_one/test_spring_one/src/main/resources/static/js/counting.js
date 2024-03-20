$(function () {

    $(".next_product").on("click", function () {
        let countProduct = $(".count_product").val()
        if (countProduct == "") {
            alert("введите количество")
            return false
        }
        addForm()
    })

    let addForm = function () {
        let generalForm = $("#add_form_product")
        let newDiv = $("<div class='new_form'></div>")
        let selectProduct = $("<select class='products'>" +
            "<option disabled>выберите продукт</option>" +
            "</select>")
        $.get("/products", {}, function (response) {
            for (i in response) {
                let name = response[i].name
                selectProduct.append($("<option>" + name + "</option>"))
            }
        })
        let countProduct = $("<input class='count_product' type='number' placeholder='количество продуктов'>")
        let nextProduct = $("<button class='next_product'>далее</button>")
        let deleteProduct = $("<button class='delete_product'>удалить</button>")
        newDiv.append(selectProduct)
            .append(countProduct)
            .append(nextProduct)
            .append(deleteProduct)
        generalForm.append(newDiv)
        $(nextProduct).on("click", function () {
            if (countProduct.val() == "") {
                alert("введите количество")
                return false
            }
            addForm()
        })
        $(deleteProduct).on("click", function () {
            newDiv.remove()
            console.log("удаление")
        })
    }

    let generalArray = []
    let nameProduct = ""
    let countProduct = ""

    $(".count_amount").on("click", function () {
        let generalForm = $(".general_form")
        generalForm.text('')
        $(".product_count").children().each(function () {
            searchAttributes(this)
            if ($(this).attr('id') == 'add_form_product') {
                $(this).children().children().each(function () {
                    searchAttributes(this)
                })
            }
        })
        $.ajax({
            url: "/counting",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            data:JSON.stringify(generalArray),
            success: function (data){
                generalForm.append(data)
                console.dir(data)
            },
            error: function (jqXHR, exception) {
                if (jqXHR.status === 0) {
                    alert('Not connect. Verify Network.');
                } else if (jqXHR.status == 404) {
                    alert('Requested page not found (404).');
                } else if (jqXHR.status == 500) {
                    alert('Internal Server Error (500).');
                } else if (exception === 'parsererror') {
                    alert('Requested JSON parse failed.');
                } else if (exception === 'timeout') {
                    alert('Time out error.');
                } else if (exception === 'abort') {
                    alert('Ajax request aborted.');
                } else {
                    alert('Uncaught Error. ' + jqXHR.responseText);
                }
            }

        })
        generalArray = []
    })

    let searchAttributes = function (attr) {
        if ($(attr).attr('class') == 'products') {
            nameProduct = $(attr).val()
            console.log(nameProduct)
        }
        if ($(attr).attr('class') == 'count_product') {
            countProduct = $(attr).val()
        }
        if (countProduct != "") {
            let transferObject = {
                nameProduct: nameProduct,
                countProduct: countProduct
            }
            generalArray.push(transferObject)
            nameProduct = ""
            countProduct = ""
        }
    }

})