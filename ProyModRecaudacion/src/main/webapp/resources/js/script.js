function filterAdvanced(filterName, ctrl) {
	var charCode = event.keyCode;
	if (ctrl.value.length == 0 || ctrl.value.length >= 3) {
		if ((charCode == 8) || (charCode == 46)
				|| (charCode >= 48 && charCode <= 57)
				|| (charCode >= 96 && charCode <= 105)
				|| (charCode >= 65 && charCode <= 90)) {
			PF(filterName).filter();
		}
	}
}