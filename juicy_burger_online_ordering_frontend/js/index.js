var json = [];

window.onload = async function(evt) {
  evt.preventDefault();
  console.log("Loading .JSON");
  const response = await fetch("res/menuItems.json");
  json = await response.json();
  console.log("Loaded .JSON");

  renderItems();
}
let clienturl = "https://876po99426.execute-api.us-west-2.amazonaws.com/client/orders";
let headers = {
  authorization: {
    'x-api-key': 'FyWu0VPqWuanyt47uz7fD3SmmCBZLRHC6Xg08JLg'
  }
}

const itemsDiv = document.querySelector("#menudiv");
const cartDiv = document.querySelector("#cartitems");
const subtotalDiv = document.querySelector("#cart-subtotal");
const taxDiv = document.querySelector("#cart-tax");
const totalDiv = document.querySelector("#cart-total");
const checkoutBtn = document.querySelector('#checkout-button');

class QuantityDrop {
  constructor(self) {
    this.itemname = self.dataset.name;
    this.itemqty = self.dataset.qty;
    this.select = document.createElement("select");
    this.select.add(new Option('Remove', 0), undefined);
    for (let i = 1; i < 100; i++) {
      this.select.add(new Option(i, i), undefined);
    }
    this.select.selectedIndex = this.itemqty;
    this.select.onchange = () => {
      (this.select.selectedIndex === 0) ? removeItem(this.itemname) :
      updateQuantity(this.itemname, this.select.selectedIndex, true);

    };
    self.append(this.select);
  }
}
class QuantityBar {
  constructor(self, decreaseText, increaseText) {
    this.input = document.createElement("input");
    this.input.value = 1;
    this.input.min = 1;
    this.input.max = 99;
    this.input.type = "number";
    this.input.oninput = () => this.quantityChanged();
    this.itemname = self.dataset.name;
    this.price = self.dataset.price;
    this.total = formatPrice(this.input.value*this.price);

    this.increaseText = increaseText || "Increase quantity";
    this.decreaseText = decreaseText || "Decrease quantity";

    function Button(text, classType) {
      this.button = document.createElement("button");
      this.button.role = "button";
      this.button.innerText = text;
      this.button.classList.add(classType);

      return this.button;
    }

    this.add = new Button(this.increaseText, "add");
    this.sub = new Button(this.decreaseText, "sub");
    this.cartbtn = new Button(`Add ${this.input.value} to Cart ($${this.total})`, "item-cartbtn");

    this.add.addEventListener("click", () => {this.input.stepUp(); this.quantityChanged();});
    this.sub.addEventListener("click", () => {this.input.stepDown(); this.quantityChanged();});
    this.cartbtn.addEventListener("click", () => {addToCart(this.itemname, this.quantityChanged())});

    self.append(this.sub);
    self.append(this.input);
    self.append(this.add);
    self.append(this.cartbtn);
  }

  quantityChanged() {
    if (isNaN(this.input.value) || this.input.value < 1) { this.input.value = 1; }
    if (this.input.value > Number(this.input.max)) { this.input.value = this.input.max; }
    this.total = formatPrice(this.input.value*this.price);
    this.cartbtn.innerText = `Add ${this.input.value} to Cart ($${this.total})`;
    return this.input.value;
  }
}

function renderItems() {
  for (let item of json) {
    let parsedname = item.name.replace(/ /g, '_');
    itemsDiv.innerHTML += `
      <div id="item-${parsedname}" class="item-base">
        <div class="item-image">
          <img src="./images/${parsedname}.png" onerror="this.onerror=null; this.src='./images/Fallback.png';" />
        </div>
        <div class="item-details">
          <div class="item-name">${item.name}</div>
          <p class="item-desc">${item.description}</p>
        </div>
        <div class="item-price">$${formatPrice(item.price)}</div>
        <div class="item-quantity" data-name="${item.name}" data-price="${item.price}"></div>
      </div>
    `;
  };
  (function(){
    let quantities = itemsDiv.querySelectorAll('.item-quantity');
    quantities.forEach(div => (div.quantity = new QuantityBar(div, 'Down', 'Up')));
  })();
}

let cart = JSON.parse(sessionStorage.getItem("CART")) || [];
updateCart();

function addToCart(name, qty) {
  qty = Number(qty);
  (cart.some((item) => item.name === name)) ? updateQuantity(name, qty) : (
    item = json.find((item) => item.name === name),
    cart.push({...item, quantity: qty})
  );
  updateCart();
}

function updateCart() {
  renderCartItems();
  renderTotal();
  sessionStorage.setItem("CART", JSON.stringify(cart));
}

function formatPrice(price) {
  price = Number(price);
  return (price/100).toFixed(2);
}


function renderTotal() {
  let subTotal = 0;
  let tax = 5; //5% tax
  let grandTotal = 0;
  cart.forEach((item) => {
    subTotal += item.price * item.quantity;
  });
  tax = Math.round((subTotal*tax)/100);
  grandTotal = subTotal + tax;
  subtotalDiv.innerText = `$${formatPrice(subTotal)}`;
  taxDiv.innerText = `$${formatPrice(tax)}`;
  totalDiv.innerText = `$${formatPrice(grandTotal)}`;
}

function renderCartItems() {
  cartDiv.innerHTML = "";
  cart.forEach((item) => {
    let parsedname = item.name.replace(/ /g, '_');
    cartDiv.innerHTML += `
      <div id="cart-${parsedname}" class="cart-base">
        <div class="cart-image">
          <img src="./images/${parsedname}.png" onerror="this.onerror=null; this.src='./images/Fallback.png';" />
        </div>
        <div class="cart-details">
          <div class="cart-name">${item.name}</div>
        </div>
        <div class="cart-price">$${formatPrice(item.price*item.quantity)}</div>
        <div class="cart-quantity" data-name="${item.name}" data-qty="${item.quantity}"></div>
      </div>
      `;
  });
  (function(){
    let quantities = cartDiv.querySelectorAll('.cart-quantity');
    quantities.forEach(div => (div.quantity = new QuantityDrop(div)));
  })();
}

function removeItem(name) {
  cart = cart.filter((item) => item.name !== name);
  updateCart();
}

function updateQuantity(name, qty, absolute=false) {
  qty = Number(qty);
  cart = cart.map((item) => {
    let quantity = Number(item.quantity);
    if (item.name === name) { (absolute) ? quantity = qty : quantity += qty; }
    return { ...item, quantity };
  });
  updateCart();
}

function checkout() {
  if (cart === undefined || cart.length == 0) return;
  const orderDescription = {};
  cart.forEach((item) => {
    orderDescription[item.name] = item.quantity;
  });
  const order = {'orderDescription': orderDescription};

  showNav(`#overlayDiv`);
//  setTimeout(function () {hideNav(`#overlayDiv`)}, 3000)
  console.log("Sending Data " + JSON.stringify(order));
  axios.post(clienturl, order, headers)
    .then((res) => {
      console.log(res);
      console.log(res.status);
      cart = [];
      updateCart();
      let returnstatus = "Something went wrong, try again later.";
      (res.status === 200) ? returnstatus = "Order Placed Successfully!" : returnstatus;
      overlayDiv.innerHTML = `<p>${returnstatus}</p>`;
//      hideNav(`#overlayDiv`);
//      window.location.reload();
    });
}

function showNav(ele) {
  document.querySelector(ele).style.height = "100%";
}
function hideNav(ele) {
  document.querySelector(ele).style.height = "0%";
}
// UUID regex:/^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$/i
//TODO axios.get page for orderId
//TODO delete api takes UUID
//TODO getorder api takes date