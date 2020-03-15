// 登录页公共 js 代码

'use strict';
const xhr = new XMLHttpRequest();

// 登录时发送异步请求获得登录结果
document.querySelector('#login').addEventListener('submit', function (e) {
	e.preventDefault();
	let checked = this.reportValidity();
	if (checked) {
		// open 应该放在设置 responseType 之上，否则会因为 readyState 为 DONE 而不能设置 responseType
		xhr.open('post', `${ctx}user/login`, true);
		xhr.responseType = "json";
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xhr.onload = function () {
			if (this.status === 200 || this.status === 302) {
				const response = this.response;
				if (response.success) {
					window.location.href = `${ctx}user/home`;
				} else {
					alert(ERR[response.code]);
				}
			}
		};
		xhr.send(formSerializer(this));
	}
});

//将 form 表单中的字段序列化为 queryString
function formSerializer (form) {
	let arr = [];
	for (let i = 0, input; (input = form.elements[i]); i++) {
		if (input.name) {
			arr.push(`${input.name}=${input.value}`);
		}
	}
	return arr.join('&');
}
