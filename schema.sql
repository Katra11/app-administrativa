create table public.customers (
  id uuid primary key default gen_random_uuid(),
  name text not null,
  phone text not null,
  nit text,
  created_at timestamptz not null default now()
);

create table public.app_users (
  id uuid primary key references auth.users(id) on delete cascade,
  name text not null,
  email text not null,
  role text not null check (role in ('ADMIN','RECEPTION','TECHNICIAN','SALES')),
  created_at timestamptz not null default now()
);

create table public.repairs (
  id uuid primary key default gen_random_uuid(),
  order_number bigserial unique,
  customer_id uuid not null references public.customers(id),
  brand text not null,
  model text not null,
  imei text,
  reported_issue text not null,
  diagnosis text,
  status text not null default 'RECIBIDO',
  total numeric(12,2) not null default 0,
  deposit numeric(12,2) not null default 0,
  assigned_technician_id uuid references public.app_users(id),
  created_at timestamptz not null default now(),
  estimated_delivery timestamptz
);

create table public.repair_photos (
  id uuid primary key default gen_random_uuid(),
  repair_id uuid not null references public.repairs(id) on delete cascade,
  storage_path text not null,
  caption text,
  created_at timestamptz not null default now()
);

create table public.inventory_items (
  id uuid primary key default gen_random_uuid(),
  name text not null,
  sku text,
  stock integer not null default 0,
  min_stock integer not null default 0,
  cost numeric(12,2) not null default 0,
  created_at timestamptz not null default now()
);

alter table public.customers enable row level security;
alter table public.app_users enable row level security;
alter table public.repairs enable row level security;
alter table public.repair_photos enable row level security;
alter table public.inventory_items enable row level security;

-- En producción: crear políticas RLS según el rol del usuario.
-- No usar service_role key dentro de la APK.
