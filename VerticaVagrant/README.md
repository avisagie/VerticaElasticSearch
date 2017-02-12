# What?

[Vertica](https://my.vertica.com/)! In a
[Vagrant](https://www.vagrantup.com/) VM!

It can take a while to figure out how to get everything to run and all
you wanted was to run some queries. For now the Vertica VM is
unavailable for download and who doesn't love Vagrant?


# How?

## Prerequisites

   1. Vagrant must be installed. This was last tested with Vagrant
      1.8.4.

   2. The VagrantFile uses the VirtualBox provider to add a disk. This
      means as it stands it only works with VirtualBox.

## Steps

   1. Clone this repository.

   2. Download the Vertica RPMs from the website (registration
      required). Put it in the same directory as Vagrantfile.

   3. `cp config.sh.sample config.sh`

   4. Edit config.sh to set the name of your Vertica RPM.

   5. `vagrant up`

   6. `vagrant ssh`


# Then what?

In the vm, after `vagrant ssh`, you typically want to do things as
dbadmin. You can, by

```
sudo su - dbadmin
```

Run vsql (the Vertica commandline client) and run queries. You can
also connect to localhost:5433 on the host machine, since the port is
forwarded to the VM.

Run `admintools` to do things to the Vertica installation.


# What's missing?

The Vertica Console is not activated by default. Look at provision.sh
to see how you would do that. After that, you can connect to
https://localhost:5450/ on the host to access and configure it.

After rebooting (with `vagrant halt` and `vagrant up`, i.e. without
[provisioning](https://www.vagrantup.com/docs/provisioning/) again,
you have to start the database manually with

```
sudo su - dbadmin
cd /vagrant
./start_database.sh
```