$(function () {

    $.get("/getPassword", {}, function (response) {
        let password = prompt("введите пароль")
        if (response == password) {
            alert("верно!")
            $("#general_div").css({display: "flex"})
        } else {
            alert("не верно:(")
            return false
        }
    })


    $.get("/products", {}, function (response) {
        let selectProd = $(".products")
        for (i in response) {
            let name = response[i].name
            selectProd.append($("<option>" + name + "</option>"))
        }
    })

    $(".save_unit").on("click", function () {
        let nameUnit = $(".input_unit").val().trim()
        if (nameUnit == "") {
            alert("введите название ед.измерения!")
            return false
        }
        $.post("/addUnit", {name: nameUnit}, function (response) {
            alert(response)
        })
    })

    $.get("/getUnits", {}, function (response) {
        let units = $(".select_unit")
        for (i in response) {
            let nameUnit = response[i].name
            units.append($("<option>" + nameUnit + "</option>"))
        }
    })

    $(".save_crude").on("click", function () {
        let crudeName = $(".input_crude").val().trim();
        if (crudeName == "") {
            alert("введите название сырья!")
            return false
        } else {
            let unitName = $(".select_unit option:selected").val()
            let transferObject = {
                name: crudeName,
                unitName: unitName
            }
            $.ajax({
                type: "POST",
                contentType: "application/json;charset=utf-8",
                url: "/addCrudes",
                data: JSON.stringify(transferObject),
                // dataType: 'json',
                success: function (response) {
                    alert(response)
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
        }
    })

    $(".delete_crude").on("click", function () {
        let nameCrude = $(".input_crude").val().trim();
        if (nameCrude == "") {
            alert("введите название сырья!")
            return false
        }
        $.post("/deleteCrude", {name: nameCrude}, function (response) {
            alert(response)
        })
    })

    let generalArr = []
    let addProduct = function () {
        let crude = $("<select class='select-crude'>" +
            "<option disabled>выберите сырьё</option>" +
            "<select/>")
        $.get("/crudes", {}, function (response) {
            for (i in response) {
                let name = response[i].name
                crude.append($("<option>" + name + "</option>"))
            }
        })
        let inputCount = $("<input required class='input-number'  type='number'>")
        let buttonNext = $("<button class='add_new'>далее</button>")
        let newDiv = $("<div class='new_div'></div>")
        newDiv.append(crude).append(inputCount).append(buttonNext)
        let productDiv = $("#add_product_div")
        productDiv.append(newDiv)

        $(".add_new").on("click", function () {
            addProduct()
        })
    }

    $(".save").on("click", function () {
        let nameProduct = $(".new_product").val().trim()
        let nameCrude = ""
        let count = ""
        $("#add_product_div").children().children().each(function () {
            console.log($(this))
            if ($(this).attr('class') == 'select-crude') {
                nameCrude = $(this).val()
            }
            if ($(this).attr('class') == 'input-number') {
                count = $(this).val()
            }
            if (count != "") {
                let transfer = {
                    productName: nameProduct,
                    crudeName: nameCrude,
                    count: count
                }
                generalArr.push(transfer)
                nameCrude = ""
                count = ""
            }
        })
        console.log(generalArr)
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            url: "/addConsolidate",
            data: JSON.stringify(generalArr),
            dataType: 'json'
        })
        alert("добавлена новая сводная для '" + nameProduct + "'!")
        deleteItems()
        generalArr = []
    })

    function deleteItems() {

        const deleteElement = document.querySelector("#add_product_div");
        deleteElement.innerHTML = '';
    }


    $(".add_product").on("click", function () {
        let newProduct = $(".new_product").val().trim()
        if (newProduct.length == 0) {
            alert("введите название продукта!")
            return false
        }
        $.post("/addNewProduct", {name: newProduct})
        console.log("запись добавлена!")
        addProduct()
    })

})