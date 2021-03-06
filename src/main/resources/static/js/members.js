// 添加一个会员
function add(){
    var modaladd = document.getElementById('modaladd');
    modaladd.style.visibility = (modaladd.style.visibility == "visible") ? "hidden" : "visible";
}
$(".membersAdd").on('click', function () {
    var tuinaName=$("#XtuinaType option:selected");
    if ($(".membersName").val() == '') {
        alert("请填写会员姓名");
    } else if ($(".pinyinCode").val() == '') {
        alert("请填写会员拼音码");
    } else if ($(".membersPhone").val() == '') {
        alert("请填写会员联系电话");
    } else if ($(".surplusNumber").val() == '') {
        alert("请填写会员剩余次数");
    } else {
        var membersPhone = $('.membersPhone').val();
        if (/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/g.test(membersPhone)) {
            // 判断手机号是否存在
            $.ajax({
                type: "PUT",
                url: "/members",
                data: {
                    membersName: $(".membersName").val(),
                    pinyinCode: $(".pinyinCode").val(),
                    membersPhone: $(".membersPhone").val(),
                    tuinaName: tuinaName.val(),
                    surplusNumber: $(".surplusNumber").val(),
                    staffId: $(".staffNames").val(),
                },
                dataType: "JSON",
                success: function (data) {
                    if (data.code == 0) {
                        alert("添加成功")
                        location.reload(true);
                    }
                    else {
                        alert("不能重复添加！")
                    }
                },
                error: function (msg) {
                    alert("添加失败")
                    location.reload(true);
                }
            })
        } else {
            alert("手机号格式错误");
        }
    }
})
// 删除会员
$(".deleteMember").on('click', function () {
    var parent = $(this).parent().parent().parent();
    var membersId = parent.children("td.membersId").text();
    var membersName = parent.children("td.membersName").text();
    var inform = "您确定要删除会员号为 " + membersName + " 的会员信息吗？";
    var r = confirm(inform);
    if (r == true) {
        $.ajax({
            type: "DELETE",
            url: "/members/delete/" + membersId,
            data: {
                membersId: membersId
            },
            dataType: "JSON",
            success: function (data) {
                alert("删除成功");
                location.reload(true);
            },
            error: function (msg) {
                location.reload(true);
            }
        })
    } else {

    }
});
// 修改
$('.closed').on('click',function () {
    $('#modalchange').css('visibility',"hidden");
});
$('.modify').on('click',function () {
    $('#modalchange').css('visibility',"visible");
    var parent = $(this).parent().parent().parent();
    var membersName = parent.children(".membersName").text();
    var pinyinCode = parent.children(".pinyinCode").text();
    var membersPhone = parent.children(".membersPhone").text();
    var surplusNumber = parent.children("td.surplusNumber").children("div.btn-group").children(".surplusNumber").text();
    var TmembersName = $(".TmembersName");
    var TpinyinCode = $(".TpinyinCode");
    var TmembersPhone = $(".TmembersPhone");
    var TsurplusNumber = $(".TsurplusNumber");
    var membersId = parent.children(".membersId").text();
    // 回显
    TmembersName.val(membersName);
    TpinyinCode.val(pinyinCode);
    TmembersPhone.val(membersPhone);
    TsurplusNumber.val(surplusNumber);

    //修改会员信息
    $('.Tmodify').on('click',function () {
        $.ajax({
            type: "PUT",
            url: "/members/Modify",
            data: {
                membersName: TmembersName.val(),
                pinyinCode : TpinyinCode.val(),
                membersPhone : TmembersPhone.val(),
                surplusNumber : TsurplusNumber.val(),
                membersId: membersId
            },
            success: function (data) {
                alert("修改成功");
                location.reload(true);
            },
            error: function (msg) {
                alert('修改失败');
            }
        })
    });
});





