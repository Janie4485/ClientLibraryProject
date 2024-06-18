$(function(){

function appendClient(client) {
    var clientItem = $('<div class="client-item"></div>');
    var clientName = $('<a href="#" class="client-link"></a>')
        .attr('data-id', client.id)
        .text(client.name);

    var clientContractNumber = $('<a href="#" class="client-link"></a>')
        .attr('data-id', client.id)
        .text(client.hetonghao);
    var clientActions = $('<div class="client-actions"></div>');
    var editLink = $('<a href="#" class="edit-client-link">Редактировать</a>')
        .attr('data-id', client.id);
    var deleteLink = $('<a href="#" class="delete-client-link">Удалить</a>')
        .attr('data-id', client.id);
    clientActions.append(editLink).append(deleteLink);
    clientItem.append(clientName).append(clientContractNumber).append(clientActions);
    $('#client-list').append(clientItem);
};
//Show adding client form
    $('#show-add-client-form').click(function(){
        $('#client-form').css('display', 'flex');
    });
//Closing adding client form
        $('#client-form').click(function(event){
            if(event.target === this) {
                $(this).css('display', 'none');
            }
        });

//Show edit client form
        $(document).on('click', '.edit-client-link', function(e) {
            e.preventDefault();

            var link = $(this);
            var clientID = link.data('id');

            if (!clientID) {
                alert('Invalid client ID');
                return false;
            }

            $.ajax({
                method: "GET",
                url: '/clients/' + clientID,
                success: function(response) {
                    $('#edit-client-id').val(response.id);
                    $('#edit-name').val(response.name);
                    $('#edit-hetonghao').val(response.hetonghao);
                    $('#edit-manager').val(response.manager);
                    $('#edit-client-form').css('display', 'block');
                },
                error: function(response) {
                    if (response.status == 404) {
                        alert('Клиент не найден!');
                    } else {
                        alert('Произошла ошибка. Статус: ' + response.status);
                    }
                }
            });
            return false;
        });


//Closing adding client form
        $('#edit-client-form').click(function(event){
            if(event.target === this) {
                $(this).css('display', 'none');
            }
        });
// Show edit-info form
  $(document).on('click', '.client-link', function(){
  $('#clientinfo-form').css('display', 'flex');
  });
//Closing client-info form
        $('#clientinfo-form').click(function(event){
            if(event.target === this) {
                $(this).css('display', 'none');
            }
        });

//Getting client in new window
$(document).on('click', '.client-link', function() {
            var link = $(this);
            var clientID = link.data('id');

            if (!clientID) {
                alert('Invalid client ID');
                return false;
            }
            $.ajax({
                method: "GET",
                url: '/clients/' + clientID,
                success: function(response) {
                    if (response.hetonghao && response.name) {
                        $('#clientName').text(response.name);
                        $('#clientContractNumber').text(response.hetonghao);
                        $('#clientManager').text(response.manager);
                        $('#clientModal').modal('show');
                    } else {
                        alert('Информация о клиенте отсутствует');
                    }
                },
                error: function(response) {
                    if (response.status == 404) {
                        alert('Клиент не найден!');
                    } else {
                        alert('Произошла ошибка. Статус: ' + response.status);
                    }
                }
            });
            return false;
        });

//Deleting client
$(document).on('click', '.delete-client-link', function(e) {
    e.preventDefault();

    var link = $(this);
    var clientID = link.data('id');

    if (clientID) {
        if (confirm('Вы уверены, что хотите удалить этого клиента?')) {
            $.ajax({
                url: '/clients/' + clientID,
                method: "DELETE",
                success: function(response) {
                    alert('Клиент удален!');

                    link.closest('.client-item').remove();
                },
                error: function(xhr, status, error) {
                    alert('Произошла ошибка при удалении клиента: ' + xhr.responseText);
                }
            });
        }
    } else {
        alert('ID клиента не найден!');
    }
});
//Edit client
$('#reset').submit(function(e) {
                e.preventDefault();

                var clientID = $('#edit-client-id').val();
                var clientData = {
                    id: clientID,
                    name: $('#edit-name').val(),
                    hetonghao: $('#edit-hetonghao').val(),
                    manager: $('#edit-manager').val()
                };

                $.ajax({
                    method: "PUT",
                    url: '/clients/' + clientID,
                    contentType: "application/json",
                    data: JSON.stringify(clientData),
                    success: function(response) {
                        alert('Данные клиента обновлены');
                        $('#edit-client-form').css('display', 'none');

                        $('a.client-link[data-id="' + clientID + '"]').filter(':first').text(response.name);
                        $('a.client-link[data-id="' + clientID + '"]').filter(':last').text(response.hetonghao);
                    },
                    error: function(xhr, status, error) {
                        alert('Произошла ошибка при сохранении клиента: ' + xhr.responseText);
                        console.error('Ошибка: ', xhr, status, error);
                    }
                });
            });

//Adding new clint
$(document).ready(function() {
    $('#save-client').click(function(event) {
        event.preventDefault();

        var data = $('#client-form form').serializeArray();
        var clientData = {};
        data.forEach(function(item) {
            clientData[item.name] = item.value;
        });

        $.ajax({
            method: "POST",
            url: '/clients/',
            contentType: "application/json",
            data: JSON.stringify(clientData),
            success: function(response) {
                $('#client-form').css('display', 'none');

                var client = {};
                client.id = response.id;
                client.name = response.name;
                client.hetonghao = response.hetonghao;

                appendClient(client);
            },
            error: function(xhr, status, error) {
                alert('Произошла ошибка при сохранении клиента: ' + xhr.responseText);
                console.error('Ошибка: ', xhr, status, error);
            }
        });

        return false;
    });

});

});