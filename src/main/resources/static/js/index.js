// variables
jQuery.fn.showV = function() {
    this.css('opacity', '1', 'border');
}

$('.mason-img').click(function() {
    $('.mason-img img').css('border', '3px solid #F08F90');
    $('.mason').showV();
});

$('.chlo-img').click(function() {
    $('.chlo-img img').css('border', '3px solid #F08F90');
    $('.chlo').showV();
});

$('.lola-img').click(function() {
    $('.lola-img img').css('border', '3px solid #F08F90');
    $('.lola').showV();
});