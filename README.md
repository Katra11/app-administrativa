# Firebase

Alternativa de backend.

Colecciones sugeridas:
- customers
- repairs
- repair_photos
- inventory_items
- app_users

Storage:
- repairs/{repairId}/{photoId}.jpg

Authentication:
- Email/password inicialmente.
- Roles mediante custom claims o documento app_users.

Seguridad:
- Nunca incluir la service account key en Android.
- Configurar Firestore Security Rules y Storage Rules antes de producción.
