<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRestrsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('restrs', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('user_id') -> unsigned() -> index();
            $table->string('name');
            $table->string('img');
            $table->text('intro');
            $table->string('region');
            $table->string('town');
            $table->string('street');
            $table->string('phone');
            $table->string('mobile');
            $table->double('lat');
            $table->double('long');
            $table->integer('is_img');
            $table->integer('views');
            $table->float('rating');
            $table->string('menu_table');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('restrs');
    }
}
