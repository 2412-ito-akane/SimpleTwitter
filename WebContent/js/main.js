//削除ボタンが押下されたときのイベント
function deleteConfirm(obj) {
	//確認ダイアログを表示
	if (confirm("このつぶやきを削除しますか？")) {
		//OKを押下したとき
		return true;
	}
	//キャンセルを押下したとき
	return false;
}