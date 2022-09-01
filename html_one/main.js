$(function () {

  $(".button-save").on("click", function () {
    let value = $(".input").val().trim();
    if (value.length == 0) {
      alert("введите что-нибудь");
    } 

    let appen = $('.select-todo')
      appen.append('<option value="">'+ value +'</option>');
      return appen;
  });

  $(".add-form").on("click", function () {
    $(".form").css("display", "inline-block");
    $(".form").css("justify-content", "center");
  });
});
