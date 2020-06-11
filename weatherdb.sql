--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12 (Ubuntu 10.12-2.pgdg18.04+1)
-- Dumped by pg_dump version 10.12 (Ubuntu 10.12-2.pgdg18.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: location; Type: TABLE; Schema: public; Owner: champa
--

CREATE TABLE public.location (
    id bigint NOT NULL,
    city_name character varying(255),
    latitude real,
    longitude real,
    state_name character varying(255)
);


ALTER TABLE public.location OWNER TO champa;

--
-- Name: location_seq; Type: SEQUENCE; Schema: public; Owner: champa
--

CREATE SEQUENCE public.location_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.location_seq OWNER TO champa;

--
-- Name: weather; Type: TABLE; Schema: public; Owner: champa
--

CREATE TABLE public.weather (
    id bigint NOT NULL,
    date_recorded date,
    temperature character varying(255),
    location_id bigint,
    highest_temp real,
    lowest_temp real
);


ALTER TABLE public.weather OWNER TO champa;

--
-- Name: weather_seq; Type: SEQUENCE; Schema: public; Owner: champa
--

CREATE SEQUENCE public.weather_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.weather_seq OWNER TO champa;

--
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: champa
--

COPY public.location (id, city_name, latitude, longitude, state_name) FROM stdin;
5	Pandharpur	17.2000008	74.8000031	Maharashtra
6	Pune	13.7799997	73.0999985	Maharashtra
\.


--
-- Data for Name: weather; Type: TABLE DATA; Schema: public; Owner: champa
--

COPY public.weather (id, date_recorded, temperature, location_id, highest_temp, lowest_temp) FROM stdin;
14	2020-06-07	20.9,22.0,22.1,23.1,23.8,24.2,24.2,25.1,25.3,26.0,31.5,33.0	5	\N	\N
15	2020-06-08	30.9,31.5,32.0,32.1,33.0,33.1,33.8,34.2,34.2,35.1,35.3,36.0	5	\N	\N
30	2020-06-08	30.8,30.9,32.1,33.0,34.2,34.2,35.1,35.3,36.0	6	\N	\N
31	2020-06-08	20.8,20.9,24.2,24.2,26.0,32.1,33.0,35.1,35.3	6	\N	\N
32	2020-06-08	20.9,24.2,24.2,26.0,32.1,33.0,35.1,35.3,38.8	5	\N	\N
33	2020-06-09	24.2,24.2,26.0,30.9,32.1,33.0,35.1,35.3,38.8	5	\N	\N
34	2020-06-09	18.5,20.2,22.0,24.2,25.3,26.0,28.8,30.9,32.1,33.0,35.1	5	35.0999985	18.5
\.


--
-- Name: location_seq; Type: SEQUENCE SET; Schema: public; Owner: champa
--

SELECT pg_catalog.setval('public.location_seq', 6, true);


--
-- Name: weather_seq; Type: SEQUENCE SET; Schema: public; Owner: champa
--

SELECT pg_catalog.setval('public.weather_seq', 34, true);


--
-- Name: location location_pkey; Type: CONSTRAINT; Schema: public; Owner: champa
--

ALTER TABLE ONLY public.location
    ADD CONSTRAINT location_pkey PRIMARY KEY (id);


--
-- Name: weather weather_pkey; Type: CONSTRAINT; Schema: public; Owner: champa
--

ALTER TABLE ONLY public.weather
    ADD CONSTRAINT weather_pkey PRIMARY KEY (id);


--
-- Name: weather fkhu6ybcv6b2oswe0tq5s27mcqf; Type: FK CONSTRAINT; Schema: public; Owner: champa
--

ALTER TABLE ONLY public.weather
    ADD CONSTRAINT fkhu6ybcv6b2oswe0tq5s27mcqf FOREIGN KEY (location_id) REFERENCES public.location(id);


--
-- PostgreSQL database dump complete
--

