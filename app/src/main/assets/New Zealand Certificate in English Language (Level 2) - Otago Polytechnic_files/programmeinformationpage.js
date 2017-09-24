/**
 * jQuery UI for the programme information pages. Loads the study breaks via AJAX,
 * creates the tabs, hover boxes, and summary details tags
 * 
 * @author torleif
 */

(function ($) {

	// inserts a single study break into the DOM
	function createstudycontainer(location, intake, studybreaks, header) {
		var header = header || false;
		var container = $('<li/>').addClass('programme-dates__item');
		if (header) {
			container.addClass('programme-dates__item--header');
		}
		container.append($('<div/>').addClass('programme-dates__item--location').html(location));
		container.append($('<div/>').addClass('programme-dates__item--intake').html(intake));
		var studybreakstr = '';
		for (var i = 0; i < studybreaks.length; i++) {
			if (studybreakstr !== '') {
				studybreakstr += '<br/>';
			}
			studybreakstr += studybreaks[i];
		}

		container.append($('<div/>').addClass('programme-dates__item--studybreak').html(studybreakstr));
		return container;
	}

	$(document).ready(function () {
		// domestic / international tabs
		$('.kis__intro--link-domestic').on('click', function (e) {
			$('#Domestic').show();
			$('#International').hide();
			e.preventDefault();
		});
		$('.kis__intro--link-international').on('click', function (e) {
			$('#Domestic').hide();
			$('#International').show();
			e.preventDefault();
		});
		var mouseinside = false;
		$('.kis h2, .kis thead').on('mouseenter', function () {
			var kisheight = $(this).offset().top - $('article').offset().top + 47;
			$('.kishoverbox').html($(this).attr('data-hover')).css('top', kisheight + 'px');
			$('.kishoverbox').show();
			mouseinside = true;
		});

		// creates the hover boxes
		$('.kishoverbox').on('mouseenter', function () {
			mouseinside = true;
		});
		$('.kis h2, .kis thead').on('mouseleave', function () {
			mouseinside = false;
			setTimeout(function () {
				if (!mouseinside) {
					$('.kishoverbox').hide();
				}
			}, 1500);
		});
		$('.kishoverbox').on('mouseleave', function () {
			mouseinside = false;
			setTimeout(function () {
				if (!mouseinside) {
					$('.kishoverbox').hide();
				}
			}, 1500);
		});

		// additional requirements
		if ($("summary:contains('requirements')").length > 0) {
			$("summary:contains('requirements')").append('<a name="DetailedRequirements"></a>');
		} else {
			$('.detailedRequirements').hide();
		}
		// find first item with 'requirements' in the name, and open it.
		$('.detailedRequirements').on('click', function (e) {
			$("summary:contains('requirements')").closest('details').attr('open', 'open');
			$("summary:contains('requirements')").get(0).scrollIntoView();
			e.preventDefault();
		});

		// fix up the summary details tags
		$('.infoSnippets').on('click', 'summary', function () {
			plussign = $(this).find('.icon-Minus').length === 0;
			if (plussign) {
				$(this).find('.icon-Plus').removeClass('icon-Plus').addClass('icon-Minus');
			} else {
				$(this).find('.icon-Minus').removeClass('icon-Minus').addClass('icon-Plus');
			}
		});

		// use AJAX to retrieve  the study breaks
		$.getJSON(document.location.pathname + '/studybreaks', function (data) {
			// if you're here, something failed
			if (!data.studybreaks) {
				return;
			} else {
				// we have data! let's build the 'table'
				if (data.studybreaks.length === 0) {
					return;
				}
				$('.programme-dates').html('').append(createstudycontainer('Location', 'Intake', new Array('Study breaks'), true));
			}

			$.each(data.studybreaks, function (index, value) {
				var studybreaks = new Array();

				if (value['BREAK1_START'] && value['BREAK1_END']) {
					studybreaks.push(value['BREAK1_START'] + ' - ' + value['BREAK1_END']);
				}
				if (value['BREAK2_START'] && value['BREAK2_END']) {
					studybreaks.push(value['BREAK2_START'] + ' - ' + value['BREAK2_END']);
				}
				if (value['BREAK3_START'] && value['BREAK3_END']) {
					studybreaks.push(value['BREAK3_START'] + ' - ' + value['BREAK3_END']);
				}
				if (value['BREAK4_START'] && value['BREAK4_END']) {
					studybreaks.push(value['BREAK4_START'] + ' - ' + value['BREAK4_END']);
				}

				var intaketext = value['START_DATE'];
				if (value['LP_INDICATIVE_START_DATES']) {
					intaketext = value['LP_INDICATIVE_START_DATES'];
				}


				$('.programme-dates').append(createstudycontainer(value.location, intaketext, studybreaks));
			});
		});
	});
}(jQuery));