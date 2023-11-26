# 990-PF XML Parser

ETL (extract/transform/load) suite which:
1. Parses filer/grant info from 990PF XMLs and stores it in a Postgres database in a table called grants.
2. Stores matching grants in a table called matching_grants.
3. Implements basic logic to normalize and clean the data.
4. Auto-generates reports for strategic funding/executive decision making.

To run this create a database in PgAdmin called 'grants' 
and change the directory path in the code to your absolute local paths for TY2013 - TY2022. 

For now, you will need to drop the grants table in PgAdmin before running the code again.

Comments/suggestions welcome!

![2021 Revenue by PF](https://github.com/RacheleRice/990PF_XML_Parser/blob/main/Images/PF1.png)

![2021 Revenue Total Sums](https://github.com/RacheleRice/990PF_XML_Parser/blob/main/Images/PF2.png)

### Future Updates ###
* JSON integration for 990 data and grants disclosed on [SEC 10-K and 10-Q Filings](https://www.sec.gov/edgar/searchedgar/companysearch.html)
    *990's are helpful to track missionsphere nonprofit revenues by year
    *10-K's and 10-Q's are helpful to track charitable contributions by publicly traded companies, not disclosed in 990-PF's
* Data Cleaning methods (in progress)
* Integrated reporting for data visualization (coming soon)
* Test cases for data validation (coming soon)
* UI so that non-technologists can use it 
* Machine learning to pull more funding sources for specified nonprofits and to automate adding 990's and 990-PF's to the database


