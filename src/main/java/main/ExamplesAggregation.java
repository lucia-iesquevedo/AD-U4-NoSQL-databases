/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ExamplesAggregation {



    public static void main(String[] args) {
        MongoDatabase mdb=null;
        MongoCollection<Document> col=null;
        //Set log
        System.setProperty("log4j.configurationFile", "log4j2.xml");

        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("zipcodes");

//MATCH
        //zipcodes from "CUSHMAN"
        // Mongo shell
        //{ $match: { city: "CUSHMAN" } }

        List<Document> list = new ArrayList<>();
        list.add(Document.parse("{ $match: { city: \"CUSHMAN\" } }"));
        col.aggregate(list).map(document -> document.getString("_id")+" belongs to CUSHMAN city").into(new ArrayList<>()).forEach(System.out::println);

//PROJECT
        //Indianapolis zipcodes population
        //Mongo shell
        //{ $match: {city: "INDIANAPOLIS"}},
        //    {$project: {_id:0, pop:1}}

        List<Document> list2 = new ArrayList<>();
        list2.add(Document.parse("{ $match: {city: \"INDIANAPOLIS\"}}"));
        list2.add(Document.parse("{$project: {pop:1}}"));
        col.aggregate(list2).map(document -> document.getInteger("pop")+": population of zipcode "+ document.getString("_id")+" from INDIANAPOLIS").into(new ArrayList<>()).forEach(System.out::println);

// REGEX
        //Cities with FALLS in their name
        //Mongo shell
        //{$match: {city: {$regex: "FALLS" }}},
        //{$project: {city:1, state:1, _id:0}}

// SPLIT & SET & EXPR & AND
        //Cities at latitude -72
        //Solution 1:
        //{$set: {latitude: {$arrayElemAt: ["$loc", 0]}}},
        //{$match: {$expr: {$and: [{$gt: ["$latitude",-73]}, {$lte: ["$latitude",-72]}]}}}

        // Solution 2:
//        {$set: {latitude: {$toString: {$arrayElemAt: ["$loc", 0]}}}},
//        {$set: {intLatitude: {$arrayElemAt: [{$split: [ "$latitude", "." ]},0]}}},
//        {$match: {intLatitude: "-72"}}

        //Solution 3:
//        {$unwind: "$loc"},
//        {$match: {$expr: {$and: [{$gt: ["$loc",-73]}, {$lte: ["$loc",-72]}]}}},
//        {$count: "Lat72"}

//COUNT
        //Number of cities at latitude -72
//        {$set: {latitude: {$arrayElemAt: ["$loc", 0]}}},
//        {$match: {$expr: {$and: [{$gt: ["$latitude",-73]}, {$lte: ["$latitude",-72]}]}}},
//        {$count: "Lat72"}


// SORT
        //list of all zipcodes ordered by state and city
        //{$sort: {state:1, city: 1}}


// SORT, LIMIT
        //ZipCode with the highest population
        //{$sort: {pop:-1}},
        //{$limit:1}


//GROUP
        //Number of zipcodes from NEW YORK
        //Mongo shell
//        { $match: { city: "NEW YORK" } },
//        { $group: { _id: "$city", total: { $sum: 1 } } }

        //Total population by state
//        Mongo shell
//        { $group: { _id: "$state", totalPop: { $sum: "$pop" } } }

//        Total Population by City
//        Mongo shell
//        { $group: { _id: { state: "$state", city: "$city" }, pop: { $sum: "$pop" } } }

//        City with the highest population
//        { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" } } },
//        {$sort: {cityPop:-1}},
//        {$limit:1}

//        all states with total population greater than 10 million
//        Mongo shell
//        { $group: { _id: "$state", totalPop: { $sum: "$pop" } } },
//        { $match: { totalPop: { $gte: 10000000 } } }

//        Average City Population by State
//        Mongo shell
//        { $group: { _id: { state: "$state", city: "$city" }, cityPop: { $sum: "$pop" } } },
//        { $group: { _id: "$_id.state", avgCityPop: { $avg: "$cityPop" } } }


//UNWIND
        //Name of student and subject with the highest mark
//        {$unwind: "$subjects"},
//        {$unwind: "$subjects.calls"},
//        {$sort: {"subjects.calls.mark":-1}},
//        {$limit: 1},
//        {$set: {subjectName: "$subjects.name"}},
//        {$set: {maxMark: "$subjects.calls.mark"}},
//        {$project: {fullname:1, subjectName:1, maxMark:1}}

    }
}
