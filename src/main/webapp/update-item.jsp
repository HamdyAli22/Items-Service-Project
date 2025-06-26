<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Update Item</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
  <link rel="stylesheet" href="css/update-item.css">
</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="text">
    Add Item
  </div>
  <form action="/item-service/ItemController">
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemId" value="${itemData.id}" readonly>
        <div class="underline"></div>
        <label>Id</label>
      </div>
      <div class="input-data">
        <input type="text" required name="itemName" value="${itemData.name}">
        <div class="underline"></div>
        <label>Name</label>
      </div>
      <div class="input-data">
        <input type="number" required name="itemPrice" min="1" step="1" value="${itemData.price}">
        <div class="underline"></div>
        <label>PRICE</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="number" required name="itemTotalNumber" min="1" step="1" value="${itemData.totalNumber}">
        <div class="underline"></div>
        <label>TOTAL_NUMBER</label>
      </div>
   
   <input type="hidden" required name="action" value="update-item">
   
    </div>
    <input type="submit" value="Update" class="button">
  </form>

  <p class="back">
    <a href="/item-service/ItemController?action=load-items" >Back To Items</a>
  </p>
</div>
<!-- partial -->

</body>
</html>
