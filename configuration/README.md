# Configuration

This folder is reserved for environment-specific deployment descriptors such as externalized properties, secrets templates, and infrastructure overlays.

## MySQL bootstrap

Create the ShopNow database and local application user with:

```bash
mysql -u root -p < configuration/mysql/init-shopnow-order-management.sql
```

Default values expected by the application `mysql` profile:

- Database: `SHOPNOW_ORDER_MANAGEMENT`
- Username: `shopnow`
- Password: `shopnow123`
