let curTime = new Date();
document.getElementById('lookupDate').valueAsDate = curTime;
document.getElementById('lookupDate').max = curTime.toISOString().split("T")[0];;


let adminurl = "https://876po99426.execute-api.us-west-2.amazonaws.com/client/admin";
let headers = {
  authorization: {
    'x-api-key': 'FyWu0VPqWuanyt47uz7fD3SmmCBZLRHC6Xg08JLg'
  }
}

let editedOrder = {};

function renderOrders(ordersList) {
  orderInfo.innerHTML = "";
  ordersList.forEach((order) => {
  let listLength = order.orderMenuItemsList.length;
  let total = (order.totalPrice/100).toFixed(2)
    orderInfo.innerHTML += `
    <div class="radioDiv">
      <input type="radio" id="${order.orderId}" name="orders">
      <label for="${order.orderId}">Items:${listLength}, Total:$${total}</label>
    </div>
    `;
  });
  ordersList.forEach((order) => {
    let radio2 = document.getElementById(order.orderId);
    radio2.onchange = () => renderEditor(order);
  });
}

function renderEditor(order) {
  editedOrder = {};
  order.orderMenuItemsList.forEach((item) => {
    editedOrder[item.name] = item.quantity;
  });
  let orderId = order.orderId;
  orderLegend.innerText = orderId;
  editingOrder.value = JSON.stringify(editedOrder, null, 2);
  editSave.onclick = () => {saveOrder(orderId)};
  editDelete.onclick = () => {deleteOrder(orderId)}

  disableInterface(false);
}

function getOrders(date){
  disableInterface(true);
  lookupDateBtn.innerText = "Loading..."
  axios.get(adminurl, {
    headers: headers,
    params: {
      salesForDate: date.value
    }
    }).then((res) => {

      disableInterface(false);
      lookupDateBtn.innerText = "Get Orders"
      renderOrders(res.data.dateSales);
  });
}

function saveOrder(orderId) {
  editedOrder = JSON.parse(editingOrder.value);
  const newOrder = {
    'orderId': orderId,
    'orderDescription': editedOrder
    };
    disableInterface(true);
    console.log("sending: " + JSON.stringify(newOrder));
    axios.put(adminurl, newOrder, {headers: headers}).then((res) => {
      disableInterface(false);
      console.log(res);
      getOrders(lookupDate);
    });

}

function deleteOrder(orderId) {
  disableInterface(true);
  axios.delete(adminurl, {
    headers: headers,
    params: { orderId: orderId }
  }).then((res) => {
    disableInterface(false);
    console.log(res);
    getOrders(lookupDate);
  });
}

function disableInterface(bool) {
  lookupDate.disabled = bool;
  lookupDateBtn.disabled = bool;
  orderEdit.disabled = bool;
  orderInfo.disabled = bool;
}