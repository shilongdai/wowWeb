(function ($) {
	"use strict";

	/*==================================================================
	[ Validate ]*/
	var input = $('.input100');

	$('.validate-form').on('submit', function () {
		var check = true;

		for (var i = 0; i < input.length; i++) {
			if (!validate(input[i])) {
				showValidate(input[i]);
				check = false;
			}
		}

		return check;
	});

	$('.validate-form').each(function () {
		$(this).focus(function () {
			hideValidate(this);
		});
	});

	function validate(input) {
		if ($(input).val().trim() == '') {
			return false;
		}
		return true;
	}

	function showValidate(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).addClass('alert-validate');
	}

	function hideValidate(input) {
		var thisAlert = $(input).parent();

		$(thisAlert).removeClass('alert-validate');
	}

})(jQuery);