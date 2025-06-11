<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Item Details</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
  <link rel="stylesheet" href="css/load-item-details.css">
</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <div class="text">
     Item Details
  </div>
  <form action="/item-service/ItemDetailsController">
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemDesc" value="${itemDetailsData.description}">
        <div class="underline"></div>
        <label>Description</label>
      </div>
      <div class="input-data">
        <input type="text" required name="itemBrand" value="${itemDetailsData.brand}">
        <div class="underline"></div>
        <label>Brand</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="text" required name="itemCategory" value="${itemDetailsData.category}">
        <div class="underline"></div>
        <label>Category</label>
      </div>
    <input type="hidden" required name="itemId" value="${itemDetailsData.itemId}">
   <input type="hidden" required name="action" value="update-details">
   
    </div>
    <input type="submit" value="Update Details" class="button">
  </form>

  <p class="back">
    <a href="/item-service/ItemController?action=load-items" >Back To Items</a>
  </p>
</div>
<!-- partial -->

</body>
</html>
